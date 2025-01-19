<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home | QuizApp</title>

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

<!-- Welcome Message -->
<h1>Welcome, ${requestScope.firstName} ${requestScope.lastName}!</h1>

<!-- Quiz Category Section -->
<div class="section">
    <h2>Start a New Quiz</h2>
    <p>Select a category to begin:</p>

    <c:if test="${not empty requestScope.categories}">
        <ul>
            <c:forEach var="category" items="${requestScope.categories}">
                <li>
                    <form method="post" action="/quiz/start">
                        <input type="hidden" name="category" value="${category.id}">
                        <button type="submit">${category.name}</button>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${empty requestScope.categories}">
        <p><strong>No quiz categories available at the moment.</strong></p>
    </c:if>
</div>

<!-- Quiz Report Section -->
<div class="section">
    <h2>Your Quiz Reports</h2>
    <p>Review your previous quizzes:</p>

    <c:if test="${not empty requestScope.quizzes}">
        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Date Taken</th>
                <th>Result</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="quiz" items="${requestScope.quizzes}">
                <tr>
                    <td>${quiz.name}</td>
                    <td>${quiz.timeStart}</td>
                    <td>
                        <form method="get" action="/quiz-result/${quiz.id}">
                            <button type="submit">View Result</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty requestScope.quizzes}">
        <p><strong>No quiz reports available.</strong></p>
    </c:if>
</div>

<!-- Logout Link -->
<div style="text-align: center; margin-top: 20px;">
    <a href="/logout" class="logout" onclick="return confirm('Are you sure you want to logout?');">Logout</a>
</div>

</body>
</html>