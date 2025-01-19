package com.bfs.logindemo.controller;

import com.bfs.logindemo.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact")
    public String showContactForm() {
        return "contact";
    }

    @PostMapping("/contact")
    public String handleContactForm(
            @RequestParam("subject") String subject,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            Model model) {

        boolean success = contactService.sendMessageToAdmin(subject, email, message);
        model.addAttribute("success", success);
        return "contact-result";
    }
}