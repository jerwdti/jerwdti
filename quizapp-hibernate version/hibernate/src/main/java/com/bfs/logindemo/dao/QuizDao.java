package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class QuizDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Quiz createQuiz(User user, Category category, Timestamp timeStart) {
        Quiz quiz = new Quiz();
        quiz.setUser(user);
        quiz.setCategory(category);
        quiz.setName(category.getName());
        quiz.setTimeStart(timeStart);
        quiz.setTimeEnd(null);

        entityManager.persist(quiz);
        return quiz;
    }

    public Quiz getQuizById(int id) {
        return entityManager.find(Quiz.class, id);
    }

    @Transactional
    public void endQuiz(Quiz quiz, Timestamp currentTime) {
        if (quiz == null) {
            throw new IllegalStateException("No active quiz to end.");
        }
        quiz.setTimeEnd(currentTime);
        entityManager.merge(quiz);
        entityManager.flush();
    }

    @Transactional
    public List<Question> generateQuestionsForQuiz(Quiz quiz, int categoryId, int limit) {
        String hql = "SELECT q FROM Question q WHERE q.category.id = :categoryId AND q.active = true ORDER BY function('RAND')";
        List<Question> questions = entityManager.createQuery(hql, Question.class)
                .setParameter("categoryId", categoryId)
                .setMaxResults(limit)
                .getResultList();

        for (Question question : questions) {
            List<Choice> choices = getChoicesForQuestion(question);
            question.getChoices().clear();
            question.getChoices().addAll(choices);
        }

        quiz.setQuestions(questions);
        generateAnswerCardsForQuiz(quiz);
        entityManager.merge(quiz);

        return questions;
    }


    @Transactional
    public List<AnswerCard> generateAnswerCardsForQuiz(Quiz quiz) {
        List<AnswerCard> answerCards = new ArrayList<>();

        for (Question question : quiz.getQuestions()) {
            // Check if the AnswerCard already exists
            List<AnswerCard> existingCards = entityManager.createQuery(
                            "SELECT ac FROM AnswerCard ac WHERE ac.quiz = :quiz AND ac.question = :question",
                            AnswerCard.class)
                    .setParameter("quiz", quiz)
                    .setParameter("question", question)
                    .getResultList();

            if (existingCards.isEmpty()) {
                AnswerCard answerCard = new AnswerCard();
                answerCard.setQuiz(quiz);
                answerCard.setQuestion(question);
                answerCard.setSelectedChoiceId(null);

                entityManager.persist(answerCard);
                answerCards.add(answerCard);
            }
        }

        entityManager.flush();
        return answerCards;
    }

    /*** Update AnswerCard with User's Selected Choice ***/
    @Transactional
    public void updateAnswerCard(Quiz quiz, Question question, Integer selectedChoiceId) {
        AnswerCard answerCard = entityManager.createQuery(
                        "SELECT ac FROM AnswerCard ac WHERE ac.quiz = :quiz AND ac.question = :question",
                        AnswerCard.class)
                .setParameter("quiz", quiz)
                .setParameter("question", question)
                .getSingleResult();

        answerCard.setSelectedChoiceId(selectedChoiceId);
        entityManager.merge(answerCard);
    }

    public List<Choice> getChoicesForQuestion(Question question) {
        String hql = "SELECT c FROM Choice c WHERE c.question.id = :questionId";
        return entityManager.createQuery(hql, Choice.class)
                .setParameter("questionId", question.getId())
                .getResultList();
    }

    public List<Map<String, Object>> generateDetailedQuizReport(Quiz quiz) {
        String hql = "SELECT q, c, ac.selectedChoiceId " +
                "FROM Question q " +
                "JOIN q.choices c " +
                "LEFT JOIN AnswerCard ac ON ac.question = q AND ac.quiz = :quiz " +
                "WHERE q.id IN (" +
                "   SELECT ac.question.id FROM AnswerCard ac WHERE ac.quiz = :quiz" +
                ")";

        List<Object[]> resultList = entityManager.createQuery(hql)
                .setParameter("quiz", quiz)
                .getResultList();

        Map<Integer, Map<String, Object>> questionMap = new LinkedHashMap<>();

        for (Object[] result : resultList) {
            Question question = (Question) result[0];
            Choice choice = (Choice) result[1];
            Integer selectedChoiceId = (Integer) result[2];

            questionMap.computeIfAbsent(question.getId(), k -> {
                Map<String, Object> details = new LinkedHashMap<>();
                details.put("questionId", question.getId());
                details.put("description", question.getDescription());
                details.put("choices", new ArrayList<Map<String, Object>>());
                details.put("selectedChoiceId", selectedChoiceId);
                return details;
            });

            // Add choice details to the question
            if (choice != null) {
                Map<String, Object> choiceDetails = new LinkedHashMap<>();
                choiceDetails.put("choiceId", choice.getId());
                choiceDetails.put("description", choice.getDescription());
                choiceDetails.put("isCorrect", choice.isCorrect());
                choiceDetails.put("isSelected", Objects.equals(choice.getId(), selectedChoiceId));

                @SuppressWarnings("unchecked")
                List<Map<String, Object>> choices = (List<Map<String, Object>>) questionMap.get(question.getId()).get("choices");
                choices.add(choiceDetails);
            }
        }

        return new ArrayList<>(questionMap.values());
    }

    @Transactional
    public Optional<Quiz> getOngoingQuizByUser(User user) {
        String hql = "SELECT q FROM Quiz q WHERE q.user = :user AND q.timeEnd IS NULL";
        List<Quiz> quizzes = entityManager.createQuery(hql, Quiz.class)
                .setParameter("user", user)
                .setMaxResults(1)
                .getResultList();

        return quizzes.stream().findFirst();
    }

    @Transactional
    public List<Quiz> getQuizByUser(User user) {
        String hql = "SELECT q FROM Quiz q WHERE q.user = :user AND q.timeEnd IS NOT NULL ORDER BY q.timeStart DESC";

        return entityManager.createQuery(hql, Quiz.class)
                .setParameter("user", user)
                .getResultList();
    }



    @Transactional
    public List<Map<String, Object>> getFilteredQuiz(Category category, User user, Integer page, Integer pageSize) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        String hql = "SELECT q.id, q.timeStart, c, u, COALESCE(COUNT(ch.id), 0) " +
                "FROM Quiz q " +
                "JOIN q.category c " +
                "JOIN q.user u " +
                "LEFT JOIN AnswerCard ac ON ac.quiz = q " +
                "LEFT JOIN Choice ch ON ac.selectedChoiceId = ch.id AND ch.isCorrect = true " +
                "WHERE (:category IS NULL OR c = :category) " +
                "AND (:user IS NULL OR u = :user) " +
                "GROUP BY q.id, q.timeStart, c, u " +
                "ORDER BY q.timeEnd DESC";

        List<Object[]> resultList = entityManager.createQuery(hql, Object[].class)
                .setParameter("category", category)
                .setParameter("user", user)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        List<Map<String, Object>> quizResults = new ArrayList<>();

        for (Object[] row : resultList) {
            Map<String, Object> details = new LinkedHashMap<>();
            details.put("quizId", row[0]);
            details.put("startTime", row[1]);
            details.put("category", row[2]);  // Category entity
            details.put("user", row[3]);      // User entity
            details.put("score", ((Long) row[4]).intValue());  // Corrected index

            quizResults.add(details);
        }

        return quizResults;
    }
}