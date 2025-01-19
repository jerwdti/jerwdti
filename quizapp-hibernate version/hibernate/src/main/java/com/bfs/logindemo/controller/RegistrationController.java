package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Return the registration page
    }

    @PostMapping("/register")
    public String handleRegistration(User user, Model model) {
        try {
            userService.createNewUser(user);
            model.addAttribute("success", "Registration successful! Please log in.");
            return "login"; // Redirect to login page with success message
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register"; // Stay on the registration page with error message
        }
    }
}