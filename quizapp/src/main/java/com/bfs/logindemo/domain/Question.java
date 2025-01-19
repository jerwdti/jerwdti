package com.bfs.logindemo.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int id;
    private String description;
    private int categoryId;
    private boolean isActive;
    private List<Choice> choices = new ArrayList<>();

    // Constructors
    public Question() {}

    public Question(int id, String description, int categoryId, boolean isActive) {
        this.id = id;
        this.description = description;
        this.categoryId = categoryId;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }

    public Choice getCorrectChoice() {
        for (Choice choice : choices) {
            if(choice.isCorrect()){
                return choice;
            }
        }
        return null;
    }

    // Override toString for debugging
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", isActive=" + isActive +
                ", choices=" + choices +
                '}';
    }


}