package com.bfs.logindemo.domain;

import java.sql.Timestamp;

public class Quiz {
    private int id;
    private int userId;
    private int categoryId;
    private String name;
    private Timestamp timeStart = null;
    private Timestamp timeEnd = null;

    // Constructors
    public Quiz() {}

    public Quiz(int id, int userId, int categoryId, String name, Timestamp timeStart, Timestamp timeEnd) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }

    // ToString Method
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }
}