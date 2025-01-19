package com.bfs.logindemo.service;

import com.bfs.logindemo.dao.QuestionDao;
import com.bfs.logindemo.domain.Question;
import com.bfs.logindemo.domain.Choice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuestionService {

    private final QuestionDao questionDao;

    @Autowired
    public QuestionService(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public Question getQuestionById(int questionId) {
        return questionDao.getQuestionById(questionId);
    }

    public void updateQuestion(Question question) {
        questionDao.updateQuestion(question);
    }


    @Transactional
    public void addQuestion(Question question) {
        questionDao.addQuestion(question);
    }

    /**
     * Delete a question by its ID.
     */
    @Transactional
    public void deleteQuestion(int questionId) {
        questionDao.deleteQuestion(questionId);
    }

    /**
     * Toggle the 'is_active' status of a question.
     */
    @Transactional
    public void toggleQuestionStatus(int questionId) {
        questionDao.toggleQuestionStatus(questionId);
    }

    /**
     * Generate a report in a table format with question category and description,
     * including all associated choices.
     */
    public List<Map<String, Object>> generateQuestionReport() {
        List<Question> questions = questionDao.getAllQuestions();
        List<Map<String, Object>> report = new ArrayList<>();

        for (Question question : questions) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("category", question.getCategory());
            row.put("question", question);

            List<String> choiceDescriptions = new ArrayList<>();
            for (Choice choice : question.getChoices()) {
                String choiceInfo = choice.getDescription() + (choice.isCorrect() ? " (Answer)" : "");
                choiceDescriptions.add(choiceInfo);
            }
            row.put("Choices", choiceDescriptions);
            String status = question.isActive()? "Active" : "Suspended";
            row.put("Status", status);

            report.add(row);
        }
        return report;
    }
}