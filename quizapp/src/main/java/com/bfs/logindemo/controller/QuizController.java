package com.bfs.logindemo.controller;

import com.bfs.logindemo.dao.QuizDao;
import com.bfs.logindemo.domain.*;
import com.bfs.logindemo.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@Controller
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/quiz/start")
    public String generateQuiz(@RequestParam("categoryId") int categoryId,
                            @RequestParam("categoryName") String categoryName,
                            HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }


        Quiz quiz = quizService.createQuiz(user.getId(), categoryId);
        quiz.setName(categoryName);

        List<Question> questions = quizService.generateQuestionsForQuiz();
        session.setAttribute("quiz", quiz);
        session.setAttribute("questions", questions);
        session.setAttribute("quizId", quiz.getId());

        List<AnswerCard> answerCards = quizService.generateAnswerCards(questions);

        session.setAttribute("answerCard", answerCards);
        model.addAttribute("quiz", quiz);
        model.addAttribute("categoryName", categoryName);
        return "quiz-start";
    }

    @GetMapping("/quiz")
    public String startQuiz(HttpSession session) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!quizService.checkStarted()) {
            quizService.startQuiz(currentTime);
            session.setAttribute("startTime", currentTime.toString());
        }

        return "redirect:/quiz-question/1";
    }

    @PostMapping("/question/{position}")
    public String handleQuestionSubmission(
            @PathVariable int position,
            @RequestParam("selectedChoiceId") int selectedChoiceId,
            HttpSession session) {


        System.out.printf("User selected choice %d for Question %d ", selectedChoiceId, position);

        List<Question> questions = (List<Question>) session.getAttribute("questions");
        if (position < 1 || position > questions.size()) {
            return "redirect:/home";
        }

        Question currentQuestion = questions.get(position - 1);
        quizService.updateAnswerCards(currentQuestion.getId(),selectedChoiceId);

        if (position < questions.size()) {
            session.setAttribute("position", position + 1);
            return "redirect:/quiz-question/" + (position + 1);
        } else {
            return "redirect:/quiz-question/" + position;
        }
    }


    @GetMapping("/quiz-question/{position}")
    public String displayQuestion(@PathVariable int position, Model model, HttpSession session) {
        List<Question> questions = (List<Question>) session.getAttribute("questions");
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        if (quiz == null || questions == null || position < 1 || position > questions.size()) {
            return "redirect:/home"; // Redirect to home if data is invalid
        }

        Question question = questions.get(position - 1);
        List<Choice> choices = question.getChoices();

        model.addAttribute("question", question);
        model.addAttribute("position", position);
        model.addAttribute("choices", choices);
        model.addAttribute("totalQuestions", questions.size());
        return "question";
    }

    @PostMapping("/quiz-result/{quizId}")
    public String submitQuiz(@PathVariable int quizId, HttpSession session) {
        Quiz quiz = (Quiz) session.getAttribute("quiz");

        if (quiz == null || quiz.getId() != quizId) {
            return "redirect:/home";
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        quizService.endQuiz(currentTime);
        session.removeAttribute("quiz");

        return "redirect:/quiz-result/" + quizId; // Redirect to result page
    }

    @GetMapping("/quiz-result/{quizId}")
    public String getQuizReport(@PathVariable int quizId, Model model) {
        // Retrieve quiz results
        Map<String, List<String>> results = quizService.getQuizResults(quizId);

        // Calculate the number of correct answers
        long correctCount = results.values().stream()
                .filter(choices -> choices.stream().anyMatch(choice ->
                        choice.contains("(Your Choice)") && choice.contains("(Correct Answer)")))
                .count();

        // Determine the quiz status
        String status = correctCount >= 3 ? "Pass" : "Fail";

        // Retrieve quiz details
        Quiz quiz = quizService.getQuizById(quizId);

        // Adding attributes to the model
        model.addAttribute("results", results);
        model.addAttribute("status", status);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("quizName", quiz.getName());
        model.addAttribute("startTime", quiz.getTimeStart());
        model.addAttribute("endTime", quiz.getTimeEnd());

        // Debugging information
        System.out.printf("Quiz ID: %d, Correct Count: %d, Status: %s%n", quizId, correctCount, status);

        return "quiz-result"; // Render quiz-result.jsp
    }
}