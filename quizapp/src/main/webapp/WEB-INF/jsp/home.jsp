<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Welcome, ${requestScope.firstName} ${requestScope.lastName}!</h1>

<div>
    <h2>Quiz Section</h2>
    <p>Select a category to start a new quiz:</p>
    <c:if test="${not empty requestScope.categories}">
        <ul>
            <c:forEach var="category" items="${requestScope.categories}">
                <li>
                    <form method="get" action="/quiz/start">
                        <input type="hidden" name="categoryId" value="${category.id}">
                        <input type="hidden" name="categoryName" value="${category.name}">
                        <button type="submit">${category.name}</button>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <c:if test="${empty requestScope.categories}">
        <p>No quiz categories available.</p>
    </c:if>
</div>

<div>
    <h2>Quiz Report Section</h2>
    <p>View your previous quiz results and reports.</p>
    <c:if test="${not empty requestScope.quizes}">
        <table border="1">
            <thead>
            <tr>
                <th>Quiz Name</th>
                <th>Date Taken</th>
                <th>Result Link</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="quiz" items="${requestScope.quizes}">
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

    <c:if test="${empty requestScope.quizes}">
        <p>No quiz reports available.</p>
    </c:if>
</div>

<div>
    <a href="/logout">Logout</a>
</div>
</body>
</html>