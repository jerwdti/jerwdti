<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Start Quiz</title>
</head>
<body>
<c:choose>
    <c:when test="${not empty requestScope.quiz}">
        <!-- Display quiz details -->
        <h1>Start Quiz for ${requestScope.categoryName}</h1>
        <p>Ready to start your quiz? Click the button below to begin!</p>

        <!-- Start Quiz Button -->
        <form method="get" action="/quiz">
            <button type="submit">START</button>
        </form>
    </c:when>
    <c:otherwise>
        <!-- Handle case where quiz is not available -->
        <h1>No Quiz Available</h1>
        <p>It seems there is no quiz to start. Please return to the home page.</p>
        <a href="/home">Return to Home</a>
    </c:otherwise>
</c:choose>
</body>
</html>