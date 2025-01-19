package com.bfs.logindemo.controller;

import com.bfs.logindemo.service.CategoryService;
import com.bfs.logindemo.service.QuizService;
import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.domain.Quiz;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private final CategoryService categoryService;
    private final QuizService quizService;

    public HomeController(CategoryService categoryService, QuizService quizService) {
        this.categoryService = categoryService;
        this.quizService = quizService;
    }

    @GetMapping("/home")
    public String displayHomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Quiz> quizes = quizService.getTop5RecentQuizzesByUser(user.getId());
        model.addAttribute("quizes", quizes);
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "home";
    }
}