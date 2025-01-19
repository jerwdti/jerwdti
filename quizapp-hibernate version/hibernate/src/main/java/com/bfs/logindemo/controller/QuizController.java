package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.*;
import com.bfs.logindemo.service.CategoryService;
import com.bfs.logindemo.service.QuizService;
import com.bfs.logindemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final CategoryService categoryService;
    private final UserService userService;

    public QuizController(QuizService quizService, CategoryService categoryService, UserService userService) {
        this.quizService = quizService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    /*** Quiz Start Page ***/
    @PostMapping("/quiz/start")
    public String showQuizStartPage(@RequestParam("category") int categoryId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Optional<Quiz> ongoingQuiz = quizService.getOngoingQuizByUser(user);
        if (ongoingQuiz.isPresent()) {
            return "redirect:/quiz-question/1?quizId=" + ongoingQuiz.get().getId();
        }

        Category category = categoryService.getCategory(categoryId);
        if (category == null) {
            model.addAttribute("error", "Selected category does not exist.");
            model.addAttribute("categories", categoryService.getAllCategories());
            return "home";
        }

        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "quiz-start";
    }

    /*** Generate a New Quiz ***/
    @PostMapping("/quiz/begin")
    public String generateQuiz(@RequestParam("category") int categoryId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Category category = categoryService.getCategory(categoryId);
        if (category == null) {
            model.addAttribute("error", "Selected category does not exist.");
            return "redirect:/home";
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Quiz quiz = quizService.createQuiz(user, category, currentTime);

        List<Question> questions = quizService.generateQuestionsForQuiz(quiz, category.getId(), 5);
        if (questions.isEmpty()) {
            model.addAttribute("error", "No active questions available for this category.");
            return "redirect:/home";
        }

        session.setAttribute("quiz", quiz);
        session.setAttribute("questions", questions);

        List<AnswerCard> answerCards = quizService.generateAnswerCardsForQuiz(quiz);
        session.setAttribute("answerCards", answerCards);

        return "redirect:/quiz-question/1?quizId=" + quiz.getId();
    }

    @PostMapping("/question/{position}")
    public String handleQuestionSubmission(
            @PathVariable int position,
            @RequestParam("selectedChoice") Integer selectedChoiceId,
            HttpSession session) {

        Quiz quiz = (Quiz) session.getAttribute("quiz");
        List<Question> questions = (List<Question>) session.getAttribute("questions");

        if (quiz == null || questions == null || position < 1 || position > questions.size()) {
            return "redirect:/home";
        }

        Question currentQuestion = questions.get(position - 1);
        quizService.updateAnswerCard(quiz, currentQuestion, selectedChoiceId);

        if (position < questions.size()) {
            return "redirect:/quiz-question/" + (position + 1);
        } else {
            return "redirect:/quiz-question/" + position;
        }
    }

    @GetMapping("/quiz-question/{position}")
    public String displayQuestion(@PathVariable int position, Model model, HttpSession session) {
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        List<AnswerCard> answerCards = (List<AnswerCard>) session.getAttribute("answerCards");

        if (quiz == null || questions == null || position < 1 || position > questions.size()) {
            return "redirect:/home";
        }

        Question currentQuestion = questions.get(position - 1);
        List<Choice> choices = currentQuestion.getChoices();

        Integer selectedChoiceId = null;
        for (AnswerCard answerCard : answerCards) {
            if (answerCard.getQuestion().getId() == currentQuestion.getId() && answerCard.getSelectedChoiceId() != null) {
                selectedChoiceId = answerCard.getSelectedChoiceId();
                break;
            }
        }

        model.addAttribute("question", currentQuestion);
        model.addAttribute("choices", choices);
        model.addAttribute("position", position);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("selectedChoiceId", selectedChoiceId);

        return "quiz-question";
    }

    /*** Submit the Quiz ***/
    @PostMapping("/quiz-result/{quizId}")
    public String submitQuiz(@PathVariable int quizId, HttpSession session) {
        Quiz quiz = (Quiz) session.getAttribute("quiz");
        if (quiz == null || quiz.getId() != quizId) {
            return "redirect:/home";
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        quizService.endQuiz(quiz, currentTime);

        session.removeAttribute("quiz");
        session.removeAttribute("questions");
        session.removeAttribute("answerCards");

        return "redirect:/quiz-result/" + quizId;
    }

    /*** Display Quiz Results ***/
    @GetMapping("/quiz-result/{quizId}")
    public String getQuizReport(@PathVariable int quizId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Quiz quiz = quizService.getQuizById(quizId);

        if (user == null || (quiz.getUser().getId() != user.getId() && !user.isAdmin())) {
            return "redirect:/home";
        }

        List<Map<String, Object>> results = quizService.getQuizResults(quiz);

        long correctCount = results.stream()
                .filter(question -> {
                    Integer selectedChoiceId = (Integer) question.get("selectedChoiceId");
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) question.get("choices");
                    return choices.stream()
                            .anyMatch(choice -> (boolean) choice.get("isCorrect") &&
                                    choice.get("choiceId").equals(selectedChoiceId));
                })
                .count();

        String status = correctCount >= 3 ? "Pass" : "Fail";

        model.addAttribute("results", results);
        model.addAttribute("userFullName", user.getFirstName() + " " + user.getLastName());
        model.addAttribute("status", status);
        model.addAttribute("quizName", quiz.getName());
        model.addAttribute("startTime", quiz.getTimeStart());
        model.addAttribute("endTime", quiz.getTimeEnd());

        return "quiz-result";
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getQuizzesByUser(@PathVariable int userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }

        List<Quiz> quizzes = quizService.getQuizByUser(user);
        if (quizzes.isEmpty()) {
            return new ResponseEntity<>("No completed quizzes found for this user.", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }
}