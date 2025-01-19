package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Choice;
import com.bfs.logindemo.domain.Quiz;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.mapper.QuizResultRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

@Repository
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;
    Quiz quiz;

    public QuizDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Quiz createQuiz(int userId, int categoryId) {
        String sql = "INSERT INTO quiz (user_id, category_id, name, time_start, time_end) " +
                "VALUES (?, ?, NULL, NULL, NULL)";
        int rowsAffected = jdbcTemplate.update(sql, userId, categoryId);
        System.out.printf("Rows affected: %d%n", rowsAffected);

        Integer quizId = null;
        String fallbackQuery = "SELECT id FROM quiz WHERE user_id = ? AND category_id = ? ORDER BY id DESC LIMIT 1";
        quizId = jdbcTemplate.queryForObject(fallbackQuery, new Object[]{userId, categoryId}, Integer.class);

        Quiz quiz = new Quiz();
        quiz.setId(quizId);
        quiz.setUserId(userId);
        quiz.setCategoryId(categoryId);
        quiz.setName(null);
        quiz.setTimeStart(null);
        quiz.setTimeEnd(null);

        this.quiz = quiz;
        return quiz;
    }

    public Quiz getQuiz(){
        return quiz;
    }

    public Quiz getQuizById(int id) {
        String sql = "SELECT id, user_id, category_id, name, time_start, time_end FROM quiz WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Quiz quiz = new Quiz();
            quiz.setId(rs.getInt("id"));
            quiz.setUserId(rs.getInt("user_id"));
            quiz.setCategoryId(rs.getInt("category_id"));
            quiz.setName(rs.getString("name"));
            quiz.setTimeStart(rs.getTimestamp("time_start"));
            quiz.setTimeEnd(rs.getTimestamp("time_end"));
            return quiz;
        });
    }

    public void startQuiz(Timestamp currentTime) {
        quiz.setTimeStart(currentTime);
    }

    public boolean checkStarted() {
        if (quiz == null || quiz.getId() == 0) {
            return false;
        }

        return quiz.getTimeStart() != null;
    }
    public void setName(String name) {
        quiz.setName(name);
    }

    public int endQuiz(Timestamp currentTime) {
        if (quiz == null || quiz.getId() == 0) {
            throw new IllegalStateException("No active quiz to end.");
        }

        String sql = "UPDATE quiz " +
                "SET user_id = ?, category_id = ?, name = ?, time_start = ?, time_end = ? " +
                "WHERE id = ?";

        quiz.setTimeEnd(currentTime);

        return jdbcTemplate.update(sql,
                quiz.getUserId(),
                quiz.getCategoryId(),
                quiz.getName(),
                quiz.getTimeStart(),
                quiz.getTimeEnd(),
                quiz.getId());
    }

    public List<Question> generateQuestionsForQuiz() {
        String sql = "SELECT q.id AS question_id, q.description AS question_text, q.category_id, q.is_active " +
                "FROM question q " +
                "WHERE q.category_id = ? AND q.is_active = TRUE " +
                "ORDER BY RAND() " +
                "LIMIT 5";

        List<Question> questions =  jdbcTemplate.query(sql, ps -> ps.setInt(1, quiz.getCategoryId()), (rs, rowNum) -> {
            Question question = new Question();
            question.setId(rs.getInt("question_id"));
            question.setDescription(rs.getString("question_text"));
            question.setCategoryId(rs.getInt("category_id"));
            question.setActive(rs.getBoolean("is_active"));
            return question;
        });

        for (Question question : questions) {
            List<Choice> choices = getChoicesForQuestion(question.getId());
            question.setChoices(choices);
        }

        return questions;
    }

    public List<Choice> getChoicesForQuestion(int questionId) {
        String sql = "SELECT c.id, c.question_id, c.description, c.is_correct " +
                "FROM choice c " +
                "JOIN question q ON c.question_id = q.id " +
                "WHERE c.question_id = ?";

        return jdbcTemplate.query(sql, ps -> ps.setInt(1, questionId), (rs, rowNum) -> {
            Choice choice = new Choice();
            choice.setId(rs.getInt("id"));
            choice.setQuestionId(rs.getInt("question_id"));
            choice.setDescription(rs.getString("description"));
            choice.setCorrect(rs.getBoolean("is_correct"));
            return choice;
        });
    }

    public Map<String, List<String>> getQuizResults(int quizId) {
        String sql = "SELECT q.description AS question_content, " +
                "c.id AS choice_id, " +
                "c.description AS choice_description, " +
                "c.is_correct, " +
                "ac.user_choice_id AS user_selected_choice " +
                "FROM question q " +
                "JOIN answer_card ac ON q.id = ac.question_id AND ac.quiz_id = ? " +
                "JOIN choice c ON q.id = c.question_id";

        Map<String, List<String>> results = new LinkedHashMap<>();

        jdbcTemplate.query(sql, new Object[]{quizId}, rs -> {
            String questionContent = rs.getString("question_content");
            List<String> choices = results.computeIfAbsent(questionContent, k -> new ArrayList<>());

            StringBuilder choiceDescription = new StringBuilder(rs.getString("choice_description"));
            if (rs.getBoolean("is_correct")) {
                choiceDescription.append(" (Correct Answer)");
            }
            if (rs.getInt("choice_id") == rs.getInt("user_selected_choice")) {
                choiceDescription.append(" (Your Choice)");
            }
            choices.add(choiceDescription.toString());
        });

        return results;
    }

    public List<Quiz> getTop5RecentQuizzesByUser(int userId) {
        String sql = "SELECT q.id AS quiz_id, q.name AS quiz_name, q.time_start, q.time_end, q.category_id " +
                "FROM quiz q " +
                "WHERE q.user_id = ? AND q.time_end IS NOT NULL " +
                "ORDER BY q.time_start DESC " +
                "LIMIT 5";

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            Quiz quiz = new Quiz();
            quiz.setId(rs.getInt("quiz_id"));
            quiz.setName(rs.getString("quiz_name"));
            quiz.setTimeStart(rs.getTimestamp("time_start"));
            quiz.setTimeEnd(rs.getTimestamp("time_end"));
            quiz.setCategoryId(rs.getInt("category_id"));
            return quiz;
        });
    }

    public List<Map<String, Object>> getQuizResults(String categoryName, String userName) {
        String sql = "SELECT q.id AS quiz_id, q.time_start AS taken_time, " +
                "cat.name AS category, " +
                "CONCAT(u.firstName, ' ', u.lastName) AS user_full_name, " +
                "COUNT(ac.question_id) AS no_of_questions, " +
                "SUM(CASE WHEN c.is_correct = 1 AND ac.user_choice_id = c.id THEN 1 ELSE 0 END) AS score " +
                "FROM quiz q " +
                "JOIN category cat ON q.category_id = cat.id " +
                "JOIN user u ON q.user_id = u.id " +
                "JOIN answer_card ac ON q.id = ac.quiz_id " +
                "JOIN choice c ON ac.question_id = c.question_id " +
                "WHERE (:categoryName IS NULL OR cat.name = :categoryName) " +
                "AND (:userName IS NULL OR CONCAT(u.firstName, ' ', u.lastName) = :userName) " +
                "GROUP BY q.id, q.time_start, cat.name, u.firstName, u.lastName " +
                "ORDER BY q.time_start DESC";

        Map<String, Object> params = new HashMap<>();
        params.put("categoryName", categoryName);
        params.put("userName", userName);

        return jdbcTemplate.queryForList(sql, params);
    }


    public List<Map<String, Object>> getFilteredQuizResults() {
        String sql = "SELECT q.id AS quiz_id, q.time_start, q.time_end, u.firstName, u.lastName, " +
                "cat.name AS category_name, " +
                "SUM(CASE WHEN c.is_correct AND ac.user_choice_id = c.id THEN 1 ELSE 0 END) AS score " +
                "FROM quiz q " +
                "JOIN user u ON q.user_id = u.id " +
                "JOIN category cat ON q.category_id = cat.id " +
                "JOIN answer_card ac ON q.id = ac.quiz_id " +
                "JOIN choice c ON ac.question_id = c.question_id " +
                "GROUP BY q.id, q.time_start, q.time_end, u.firstName, u.lastName, cat.name " +
                "ORDER BY q.time_start DESC LIMIT 10 OFFSET 0";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("quizId", rs.getInt("quiz_id"));
            row.put("timeStart", rs.getTimestamp("time_start"));
            row.put("timeEnd", rs.getTimestamp("time_end"));
            row.put("userFullName", rs.getString("firstName") + " " + rs.getString("lastName"));
            row.put("categoryName", rs.getString("category_name"));
            row.put("score", rs.getInt("score"));
            return row;
        });
    }

}
