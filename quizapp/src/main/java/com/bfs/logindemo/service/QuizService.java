package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.AnswerCardDao;
import com.bfs.logindemo.dao.QuizDao;
import com.bfs.logindemo.domain.AnswerCard;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.Question;
import org.springframework.stereotype.Service;

import java.util.List;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class QuizService {

    private final QuizDao quizDao;
    AnswerCardDao answerCardDao;

    public QuizService(QuizDao quizDao, AnswerCardDao answerCardDao) {
        this.quizDao = quizDao;
        this.answerCardDao = answerCardDao;
    }

    public Quiz createQuiz(int userId, int categoryId) {
        if(quizDao != null && quizDao.getQuiz() != null ) {
            return quizDao.getQuiz();
        }
        return quizDao.createQuiz(userId, categoryId);
    }

    public void startQuiz(Timestamp currentTime) {
        quizDao.startQuiz(currentTime);
    }

    public boolean checkStarted() {
        return quizDao.checkStarted();
    }

    public List<Question> generateQuestionsForQuiz() {
        return quizDao.generateQuestionsForQuiz();
    }

    public void endQuiz(Timestamp currentTime) {
        quizDao.endQuiz(currentTime);

    }

    public Quiz getQuizById(int quizId) {
        return quizDao.getQuizById(quizId);
    }

    public List<AnswerCard> generateAnswerCards(List<Question> questions){
        return answerCardDao.generateAnswerCards(quizDao.getQuiz().getId(), questions);
    }

    public void updateAnswerCards(int questionId, int userChoiceId){
        answerCardDao.updateAnswerCard(quizDao.getQuiz().getId(), questionId, userChoiceId);
    }

    public Map<String, List<String>> getQuizResults(int quizId) {
        return quizDao.getQuizResults(quizId);
    }

    public List<Quiz> getTop5RecentQuizzesByUser(int userId) {
        return quizDao.getTop5RecentQuizzesByUser(userId);
    }

    public List<Map<String, Object>> getFilteredQuizResults(String categoryName, String userName) {
        return quizDao.getQuizResults(categoryName, userName);
    }

    public List<Map<String, Object>> getFilteredQuizResults() {
        return quizDao.getFilteredQuizResults();
    }

}