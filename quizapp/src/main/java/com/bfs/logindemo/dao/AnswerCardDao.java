package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.AnswerCard;
import com.bfs.logindemo.domain.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Repository
public class AnswerCardDao {

    private final JdbcTemplate jdbcTemplate;

    public AnswerCardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AnswerCard> generateAnswerCards(int quizId, List<Question> questions) {
        String sql = "INSERT INTO answer_card (quiz_id, question_id, user_choice_id) VALUES (?, ?, NULL)";

        List<AnswerCard> answerCards = new ArrayList<>();

        for (Question question : questions) {
            System.out.printf("%d, %d \n", quizId, question.getId());
            jdbcTemplate.update(sql, quizId, question.getId());
            AnswerCard answerCard = new AnswerCard();
            answerCard.setQuizId(quizId);
            answerCard.setQuestionId(question.getId());
            answerCard.setAnswerId(null); // No answer yet
            answerCards.add(answerCard);
        }

        return answerCards;
    }

    public void updateAnswerCard(int quizId, int questionId, int userChoiceId) {
        String sql = "UPDATE answer_card " +
                "SET user_choice_id = ? " +
                "WHERE quiz_id = ? AND question_id = ?";
        jdbcTemplate.update(sql, userChoiceId, quizId, questionId);
    }
}