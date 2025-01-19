-- Create database and switch to it
CREATE DATABASE IF NOT EXISTS qwp_database;
USE qwp_database;

-- Drop existing tables (in reverse dependency order)
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS quiz_question;
DROP TABLE IF EXISTS choice;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS quiz;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS user;

SET FOREIGN_KEY_CHECKS = 1;

-- Create tables
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE quiz (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    name VARCHAR(100) NULL,
    time_start TIMESTAMP NULL,
    time_end TIMESTAMP NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE question (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    description TEXT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE, -- Default to TRUE for usability
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE choice (
    id INT AUTO_INCREMENT,
    question_id INT NOT NULL,
    description TEXT NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id, question_id),
    FOREIGN KEY (question_id) REFERENCES question(id)
);

CREATE TABLE quiz_question (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quiz_id INT NOT NULL,
    question_id INT NOT NULL,
    user_choice_id INT, -- Allow NULL for unanswered questions
    choice_question_id INT,
    FOREIGN KEY (quiz_id) REFERENCES quiz(id),
    FOREIGN KEY (question_id) REFERENCES question(id),
    FOREIGN KEY (user_choice_id, choice_question_id) REFERENCES choice(id, question_id)
);
