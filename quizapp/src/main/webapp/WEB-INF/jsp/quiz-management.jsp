<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Quiz Results Management</title>
</head>
<body>
<h1>Quiz Results Management</h1>

<!-- Filters Section -->
<form method="get" action="/admin/quiz-results">
    <label for="categoryName">Category:</label>
    <input type="text" id="categoryName" name="categoryName" value="${param.categoryName}">

    <label for="userName">User Name:</label>
    <input type="text" id="userName" name="userName" value="${param.userName}">

    <button type="submit">Filter</button>
</form>

<hr>

<!-- Quiz Results Table -->
<table border="1">
    <thead>
    <tr>
        <th>Taken Time</th>
        <th>Category</th>
        <th>User Full Name</th>
        <th>No. of Questions</th>
        <th>Score</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="result" items="${quizResults}">
        <tr>
            <td>${result.timeStart}</td>
            <td>${result.categoryName}</td>
            <td>${result.userFullName}</td>
            <td>${result.score}</td>
            <td><a href="/quiz-result/${result.quizId}">View Details</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty quizResults}">
    <p>No quiz results found.</p>
</c:if>

</body>
</html>