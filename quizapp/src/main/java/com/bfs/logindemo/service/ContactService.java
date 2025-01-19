package com.bfs.logindemo.service;

import org.springframework.stereotype.Service;

@Service
public class ContactService {
    public boolean sendMessageToAdmin(String subject, String email, String message) {

        System.out.println("Subject: " + subject);
        System.out.println("Email: " + email);
        System.out.println("Message: " + message);
        return true;
    }
}