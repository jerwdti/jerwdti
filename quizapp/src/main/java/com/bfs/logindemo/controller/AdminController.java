package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.QuizService;
import com.bfs.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    private final UserService userService;
    private final QuizService quizService;

    @Autowired
    public AdminController(UserService userService, QuizService quizService) {
        this.userService = userService;
        this.quizService = quizService;
    }

    // User Management
    @GetMapping("/admin/user-management")
    public String getUsers(@RequestParam(defaultValue = "1") int page, Model model) {
        int pageSize = 5; // Users per page
        List<User> users = userService.getPaginatedUsers(page, pageSize);
        int totalUsers = userService.getTotalUserCount();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "user-management";
    }

    @PostMapping("/admin/user-management/toggle-status")
    public String toggleUserStatus(@RequestParam int userId, Model model) {
        userService.toggleUserStatus(userId);
        model.addAttribute("successMessage", "User status updated successfully.");
        return "redirect:/admin/user-management";
    }


    @GetMapping("/admin/quiz-management")
    public String getQuizResult(Model model) {

        List<Map<String, Object>> quizResults = quizService.getFilteredQuizResults();
        model.addAttribute("quizResults", quizResults);

        return "quiz-management";
    }

    @GetMapping("/admin")
    public String displayAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if(!user.isAdmin()){
            return "redirect:/home";
        }

        return "admin";
    }
}