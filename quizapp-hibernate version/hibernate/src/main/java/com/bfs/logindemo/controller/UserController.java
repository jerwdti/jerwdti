package com.bfs.logindemo.controller;

import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            userService.createNewUser(user);
            return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestParam("userId") int userId) {
        User user = userService.getUser(userId);
        if (user != null) {
            userService.deleteUser(userId);
            return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
    }

    // Activate or Suspend a user
    @PatchMapping
    public ResponseEntity<String> updateUserStatus(@RequestParam("userId") int userId, @RequestParam("activate") boolean activate) {
        User user = userService.getUser(userId);
        if (user != null) {
            if (user.isActive() == activate) {
                String status = activate ? "already active." : "already suspended.";
                return new ResponseEntity<>("User is " + status, HttpStatus.OK);
            }

            userService.updateUserStatus(userId, activate);
            String status = activate ? "activated" : "suspended";
            return new ResponseEntity<>("User has been " + status + " successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(value = "userId", required = false) Integer userId) {
        if (userId != null) {
            // Fetch user by ID if userId is provided
            User user = userService.getUser(userId);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
            }
        } else {
            // Fetch all users if userId is not provided
            try {
                List<User> users = userService.getAllUsers();
                if (users.isEmpty()) {
                    return new ResponseEntity<>("No users found.", HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(users, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error fetching users.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}