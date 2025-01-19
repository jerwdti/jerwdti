<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Quiz Results</title>
</head>
<body>
<h1>Quiz Results</h1>

<!-- Quiz Information -->
<div>
    <h2>Quiz: ${quiz.name}</h2>
    <p>Status: <strong>${status}</strong></p>
    <p>Start Time: ${startTime}</p>
    <p>End Time: ${endTime}</p>
</div>

<hr>

<!-- Questions and Answers -->
<c:forEach var="entry" items="${results}">
    <div>
        <h3>Question: ${entry.key}</h3>
        <ul>
            <c:forEach var="choice" items="${entry.value}">
                <li>${choice}</li>
            </c:forEach>
        </ul>
    </div>
    <hr>
</c:forEach>

<!-- Return to Home -->
<div>
    <a href="/home">Return to Home</a>
</div>
</body>
</html>