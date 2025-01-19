package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.*;
import com.bfs.logindemo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final QuizService quizService;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final ContactService contactService;

    @Autowired
    public AdminController(
            UserService userService,
            QuizService quizService,
            CategoryService categoryService,
            QuestionService questionService,
            ContactService contactService) {
        this.userService = userService;
        this.quizService = quizService;
        this.categoryService = categoryService;
        this.questionService = questionService;
        this.contactService = contactService;
    }

    /*** Admin Home ***/
    @GetMapping
    public String displayAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        if (!user.isAdmin()) {
            return "redirect:/home";
        }
        return "admin";
    }

    /*** User Management Page ***/
    @GetMapping("/user-management")
    public String getUsers(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 5;
        List<User> users = userService.getPaginatedUsers(page, pageSize);
        int totalUsers = userService.getTotalUserCount();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "user-management";
    }

    @PostMapping("/user-management/toggle-status")
    public String toggleUserStatus(@RequestParam int userId) {
        userService.toggleUserStatus(userId);
        return "redirect:/admin/user-management";  // PRG pattern
    }

    @GetMapping("/quiz-management")
    public String getFilteredQuizResults(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "userId", required = false) Integer userId,
            Model model) {

        Category category = null;
        User user = null;

        if (categoryId != null) {
            category = categoryService.getCategory(categoryId);
        }

        if (userId != null) {
            user = userService.getUser(userId);
        }
        List<Map<String, Object>> quizResults = quizService.getFilteredQuizzes(category, user);

        model.addAttribute("quizResults", quizResults);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentPage", 1);
        model.addAttribute("pageSize", 10);

        return "quiz-management";
    }

    // Display all questions
    @GetMapping("/question-management")
    public String viewQuestions(Model model) {
        List<Map<String, Object>> questionReport = questionService.generateQuestionReport();

        model.addAttribute("questionReport", questionReport);
        return "question-management";
    }

    @GetMapping("/question-add")
    public String showAddQuestionPage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "question-add";
    }


    @PostMapping("/question-add")
    public String addQuestion(
            @RequestParam("description") String description,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("choice1") String choice1,
            @RequestParam("choice2") String choice2,
            @RequestParam("choice3") String choice3,
            @RequestParam("correctChoice") Integer correctChoiceId) {

        // Fetch the selected category
        Category category = categoryService.getCategory(categoryId);

        // Create and save the question
        Question question = new Question();
        question.setDescription(description);
        question.setCategory(category);
        question.setActive(true);

        // Create and add choices
        List<Choice> choices = new ArrayList<>();
        String[] choiceDescriptions = {choice1, choice2, choice3};

        for (int i = 0; i < 3; i++) {
            Choice choice = new Choice();
            choice.setDescription(choiceDescriptions[i]);
            choice.setCorrect((i + 1) == correctChoiceId); // Mark the correct choice
            choice.setQuestion(question);
            choices.add(choice);
        }

        question.setChoices(choices);

        questionService.addQuestion(question);

        return "redirect:/admin/question-management";
    }

    // Delete a question
    @PostMapping("/question/delete")
    public String deleteQuestion(@RequestParam("questionId") int questionId) {
        questionService.deleteQuestion(questionId);
        return "redirect:/admin/question-management";
    }

    // Toggle question's active status
    @PostMapping("/question/toggle-status")
    public String toggleQuestionStatus(@RequestParam("questionId") int questionId) {
        questionService.toggleQuestionStatus(questionId);
        return "redirect:/admin/question-management";
    }

    @GetMapping("/question-edit/{id}")
    public String showEditQuestionPage(@PathVariable("id") Integer id, Model model) {
        Question question = questionService.getQuestionById(id);
        model.addAttribute("question", question);
        return "question-edit";
    }

    @PostMapping("/question-edit/{id}")
    public String editQuestion(
            @PathVariable("id") Integer id,
            @RequestParam("description") String description,
            @RequestParam("choice1") String choice1,
            @RequestParam("choice2") String choice2,
            @RequestParam("choice3") String choice3,
            @RequestParam("correctChoice") Integer correctChoiceIndex) {

        Question question = questionService.getQuestionById(id);
        question.setDescription(description);

        List<Choice> choices = question.getChoices();
        String[] updatedChoices = {choice1, choice2, choice3};

        for (int i = 0; i < 3; i++) {
            Choice choice = choices.get(i);
            choice.setDescription(updatedChoices[i]);
            choice.setCorrect((i + 1) == correctChoiceIndex);
        }

        questionService.updateQuestion(question);

        return "redirect:/admin/question-management";
    }

    @GetMapping("/contact-management")
    public String showAllMessages(Model model) {
        List<Contact> messages = contactService.getAllMessages();
        model.addAttribute("messages", messages);
        return "contact-management";
    }
}