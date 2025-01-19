<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Quiz Results Management</title>
</head>
<body>

<h1>Quiz Results Management</h1>

<hr>

<!-- Filter Form -->
<form action="/admin/quiz-management" method="get">
    <!-- Category Filter (Optional) -->
    <label for="categoryId">Category:</label>
    <select name="categoryId" id="categoryId">
        <option value="">-- All Categories --</option>
        <c:forEach var="category" items="${categories}">
            <option value="${category.id}" ${param.categoryId == category.id ? 'selected' : ''}>
                    ${category.name}
            </option>
        </c:forEach>
    </select>

    <!-- User Filter (Optional) -->
    <label for="userId">User:</label>
    <select name="userId" id="userId">
        <option value="">-- All Users --</option>
        <c:forEach var="user" items="${users}">
            <option value="${user.id}" ${param.userId == user.id ? 'selected' : ''}>
                    ${user.firstName} ${user.lastName}
            </option>
        </c:forEach>
    </select>

    <!-- Submit Button -->
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
        <th>Quiz Report</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="result" items="${quizResults}">
        <tr>
            <td>${result.startTime}</td>
            <td>${result.category.name}</td>
            <td>${result.user.firstName} ${result.user.lastName}</td>
            <td>5</td>
            <td>${result.score}</td>
            <td><a href="/quiz-result/${result.quizId}">View Details</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Pagination Controls -->
<div>
    <c:if test="${page > 1}">
        <a href="?page=${page - 1}&pageSize=${pageSize}">Previous</a>
    </c:if>

    Page ${page}

    <c:if test="${quizResults.size() == pageSize}">
        <a href="?page=${page + 1}&pageSize=${pageSize}">Next</a>
    </c:if>
</div>

<!-- Show Message if No Results -->
<c:if test="${empty quizResults}">
    <p>No quiz results found.</p>
</c:if>

</body>
</html>