package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuizDao;
import com.bfs.logindemo.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizDao quizDao;

    public QuizService(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Transactional
    public Quiz createQuiz(User user, Category category, Timestamp timeStart) {
        return quizDao.createQuiz(user, category, timeStart);
    }


    public List<Question> generateQuestionsForQuiz(Quiz quiz, int categoryId, int limit) {
        return quizDao.generateQuestionsForQuiz(quiz, categoryId, limit);
    }

    @Transactional
    public void endQuiz(Quiz quiz, Timestamp currentTime) {
        quizDao.endQuiz(quiz, currentTime);
    }

    public Optional<Quiz> getOngoingQuizByUser(User user) {
        return quizDao.getOngoingQuizByUser(user);
    }

    public List<Map<String, Object>> getQuizResults(Quiz quiz) {
        return quizDao.generateDetailedQuizReport(quiz);
    }

    public List<Map<String, Object>> getFilteredQuizzes(Category category, User user) {
        return quizDao.getFilteredQuiz(category, user, 1, 20);
    }

    public List<AnswerCard> generateAnswerCardsForQuiz(Quiz quiz) {
        return quizDao.generateAnswerCardsForQuiz(quiz);
    }

    public void updateAnswerCard(Quiz quiz, Question question, Integer selectedChoiceId) {
        quizDao.updateAnswerCard(quiz,question,selectedChoiceId);
    }

    public Quiz getQuizById(int quizId) {
        return quizDao.getQuizById(quizId);
    }

    public List<Quiz> getQuizByUser(User user) {
        return quizDao.getQuizByUser(user);
    }


}