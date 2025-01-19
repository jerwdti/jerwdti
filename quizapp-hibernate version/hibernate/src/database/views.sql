CREATE OR REPLACE VIEW QuestionsWithChoices AS
SELECT 
    q.id AS question_id,
    q.description AS question_text,
    q.category_id,
    c.id AS choice_id,
    c.description AS choice_text,
    c.is_correct,
    cat.name AS category_name
FROM 
    question q
LEFT JOIN 
    choice c ON q.id = c.question_id
LEFT JOIN 
    category cat ON q.category_id = cat.id
WHERE 
    q.is_active = TRUE;