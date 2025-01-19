package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Question;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class QuestionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addQuestion(Question question) {
        entityManager.persist(question);
    }

    @Transactional
    public void updateQuestion(Question question) {
        entityManager.merge(question);
    }

    @Transactional
    public void deleteQuestion(int questionId) {
        Question question = entityManager.find(Question.class, questionId);
        if (question != null) {
            question.getChoices().forEach(entityManager::remove);
            entityManager.remove(question);
        }
    }

    @Transactional
    public void toggleQuestionStatus(int questionId) {
        Question question = entityManager.find(Question.class, questionId);
        if (question != null) {
            question.setActive(!question.isActive());
            entityManager.merge(question);
        }
    }

    @Transactional
    public Question getQuestionById(Integer id) {
        Question question = entityManager.find(Question.class, id);
        if (question != null) {
            Hibernate.initialize(question.getCategory());  // Eagerly load category
            Hibernate.initialize(question.getChoices());   // Eagerly load choices
        }
        return question;
    }


    public List<Question> getAllQuestions() {
        String hql = "SELECT DISTINCT q FROM Question q " +
                "JOIN FETCH q.category " +
                "LEFT JOIN FETCH q.choices";
        return entityManager.createQuery(hql, Question.class).getResultList();
    }
}