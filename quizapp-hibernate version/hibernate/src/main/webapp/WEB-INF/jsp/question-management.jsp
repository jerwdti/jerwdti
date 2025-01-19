<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Question Management</title>
</head>
<body>

<h1>Question Management</h1>

<!-- Form to Add New Question -->
<h2>Add New Question</h2>
<a href="/admin/question-add">Add Question</a>
<hr>

<!-- Question Report Table -->
<h2>All Questions Report</h2>
<table border="1">
    <thead>
    <tr>
        <th>Category</th>
        <th>Question</th>
        <th>Choices</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="reportRow" items="${questionReport}">
        <tr>
            <!-- Category -->
            <td>${reportRow.category.name}</td>

            <!-- Question -->
            <td>${reportRow.question.description}</td>

            <!-- Choices -->
            <td>
                <ul style="list-style-type: none; padding: 0; margin: 0;">
                    <c:forEach var="choice" items="${reportRow.Choices}">
                        <li>${choice}</li>
                    </c:forEach>
                </ul>
            </td>

            <!-- Status -->
            <td>${reportRow.Status}</td>

            <!-- Actions -->
            <td>
                <!-- Edit Button -->
                <form method="get" action="/admin/question-edit/${reportRow.question.id}" style="display:inline;">
                    <button type="submit">Edit</button>
                </form>

                <!-- Toggle Status -->
                <form method="post" action="/admin/question/toggle-status" style="display:inline;">
                    <input type="hidden" name="questionId" value="${reportRow.question.id}">
                    <button type="submit">
                        <c:choose>
                            <c:when test="${reportRow.Status == 'Active'}">Deactivate</c:when>
                            <c:otherwise>Activate</c:otherwise>
                        </c:choose>
                    </button>
                </form>

                <!-- Delete Button -->
                <form method="post" action="/admin/question/delete" style="display:inline;">
                    <input type="hidden" name="questionId" value="${reportRow.question.id}">
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this question?')">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty questionReport}">
    <p>No questions found.</p>
</c:if>

</body>
</html>