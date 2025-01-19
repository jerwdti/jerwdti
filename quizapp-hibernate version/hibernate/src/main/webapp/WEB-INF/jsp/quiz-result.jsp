<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Quiz Results</title>
    <style>

        /* Global Styles */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
            color: #333;
        }

        /* Navigation Bar */
        .navbar {
            background-color: #007bff;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar a.logo {
            color: #fff;
            font-size: 24px;
            text-decoration: none;
        }

        .navbar ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
        }

        .navbar ul li {
            margin-left: 20px;
        }

        .navbar ul li a {
            color: #fff;
            text-decoration: none;
            font-size: 16px;
        }

        .navbar ul li a:hover {
            text-decoration: underline;
        }

        /* Buttons */
        button, .btn {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        button:hover, .btn:hover {
            background-color: #0056b3;
        }

        /* Form Inputs */
        input[type="text"], input[type="password"], input[type="email"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        /* Form Container */
        .form-container {
            width: 100%;
            max-width: 400px;
            margin: auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

    </style>
</head>
<body>

<h1>Quiz Results</h1>

<div class="content">
    <h2>Quiz: ${quizName}</h2>
    <p>User Name: ${userFullName}</p>
    <p>Start Time: ${startTime}</p>
    <p>End Time: ${endTime}</p>
    <p>Result: <strong>${status}</strong></p>
</div>

<!-- Iterate over the list of questions -->
<c:forEach var="question" items="${results}">
    <div class="content">
        <h3>Question: ${question.description}</h3>
        <ul>
            <!-- Iterate over the choices of each question -->
            <c:forEach var="choice" items="${question.choices}">
                <li class="
                    <c:choose>
                        <c:when test='${choice.choiceId == question.selectedChoiceId && choice.isCorrect}'>
                            correct-selected
                        </c:when>
                        <c:when test='${choice.choiceId == question.selectedChoiceId && !choice.isCorrect}'>
                            incorrect-selected
                        </c:when>
                        <c:when test='${choice.isCorrect && choice.choiceId != question.selectedChoiceId}'>
                            correct
                        </c:when>
                    </c:choose>
                ">
                        ${choice.description}
                    <c:if test='${choice.choiceId == question.selectedChoiceId}'>
                        <strong>(Your Choice)</strong>
                    </c:if>
                    <c:if test='${choice.isCorrect && choice.choiceId == question.selectedChoiceId}'>
                        <strong>(Correct Answer)</strong>
                    </c:if>
                </li>
            </c:forEach>
        </ul>
    </div>
</c:forEach>

</body>
</html>