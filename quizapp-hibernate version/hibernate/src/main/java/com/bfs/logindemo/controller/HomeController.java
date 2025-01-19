// HomeController.java
package com.bfs.logindemo.controller;

import com.bfs.logindemo.service.CategoryService;
import com.bfs.logindemo.service.QuizService;
import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.exception.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final CategoryService categoryService;
    private final QuizService quizService;

    public HomeController(CategoryService categoryService, QuizService quizService) {
        this.categoryService = categoryService;
        this.quizService = quizService;
    }

    @GetMapping("/home")
    public String displayHomePage(@RequestParam(value = "userid", required = false) Integer userId,
                                  HttpSession session,
                                  Model model) {
        Instant startTime = Instant.now();
        logger.info("[START] Accessing /home at {} with userId: {}", startTime, userId);

        User user = (User) session.getAttribute("user");

        if (user == null) {
            logger.warn("Unauthorized access attempt to /home page.");
            throw new UserNotLoggedInException("User is not logged in.");
        }

        List<Quiz> quizzes = quizService.getQuizByUser(user);
        logger.info("Loaded {} quizzes for user: {}", quizzes.size(), user.getFirstName());

        model.addAttribute("quizzes", quizzes);
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("categories", categoryService.getAllCategories());

        Instant endTime = Instant.now();
        logger.info("[END] Finished processing /home at {}. Duration: {} ms", endTime, endTime.toEpochMilli() - startTime.toEpochMilli());

        return "home";
    }
}
