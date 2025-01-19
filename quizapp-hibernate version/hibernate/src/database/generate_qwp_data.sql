-- Insert data
INSERT INTO user (firstName, lastName, email, password, is_admin) VALUES
("admin", "admin", "admin@test.com", "calvin123", TRUE),
("Jack", "Shi", "jack@test.com", "123", FALSE),
("Aiden", "Gong", "aiden@test.com", "123", FALSE);

INSERT INTO category (name) VALUES
("Math"), -- 1
("Computer Science"), -- 2
("History"), -- 3
("Other"); -- 4

INSERT INTO question (id, category_id, description, is_active) VALUES
(1, 1, "Pick the value that is negative", TRUE),
(2, 1, "Pick the value that is in a common logarithm expression", TRUE),
(3, 1, 'What is 2 + 2?', TRUE),
(4, 1, 'What is the square root of 16?', TRUE),
(5, 1, 'What is 10 divided by 2?', TRUE),
(6, 1, 'Pick the value that is 3 in binary', True),
(7, 1, 'Pick the value that is 10010 in Hexdecimal', True),
(8, 1, 'Pick the value that is 7 in Roman numerial', True),
(9, 1, 'Pick the value that is a Prime number', True),
(10, 1, 'What is x, if x + y = 5 and y = 3', True);

-- Question 1: Pick the value that is negative
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(1, 1, '-5', TRUE),
(2, 1, '5', FALSE),
(3, 1, '0', FALSE);

-- Question 2: Pick the value that is in a common logarithm expression
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(4, 2, 'log(10)', TRUE),
(5, 2, 'ln(10)', FALSE),
(6, 2, '10^2', FALSE);

-- Question 3: What is 2 + 2?
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(7, 3, '0', FALSE),
(8, 3, '4', TRUE),
(9, 3, '8', FALSE);

-- Question 4: What is the square root of 16?
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(10, 4, '9', FALSE),
(11, 4, '8', FALSE),
(12, 4, '4', TRUE);

-- Question 5: What is 10 divided by 2?
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(13, 5, '5.5', FALSE),
(14, 5, '5.0', TRUE),
(15, 5, '3.17', FALSE);

-- Question 6: Pick the value that is 3 in binary
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(16, 6, '0011', TRUE),
(17, 6, '0101', FALSE),
(18, 6, '1100', FALSE);

-- Question 7: Pick the value that is 10010 in Hexadecimal
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(19, 7, '12', FALSE),
(20, 7, '92', FALSE),
(21, 7, '22', TRUE);

-- Question 8: Pick the value that is 7 in Roman numerals
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(22, 8, 'VII', TRUE),
(23, 8, 'VIII', FALSE),
(24, 8, 'IV', FALSE);

-- Question 9: Pick the value that is a Prime number
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(25, 9, '9', FALSE),
(26, 9, '11', TRUE),
(27, 9, '15', FALSE);

-- Question 10: What is x, if x + y = 5 and y = 3?
INSERT INTO choice (id, question_id, description, is_correct) VALUES
(28, 10, '3', FALSE),
(29, 10, '2', TRUE),
(30, 10, '5', FALSE);


