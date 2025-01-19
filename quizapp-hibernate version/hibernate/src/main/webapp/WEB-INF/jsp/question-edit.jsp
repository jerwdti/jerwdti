<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Edit Question</title>
</head>
<body>

<h1>Edit Question</h1>

<!-- Edit Question Form -->
<form method="post" action="/admin/question-edit/${question.id}">
    <!-- Question Description -->
    <label for="description">Question Description:</label><br>
    <textarea id="description" name="description" rows="3" cols="50" required>${question.description}</textarea>
    <br><br>

    <!-- Category (Read-Only) -->
    <label>Category:</label><br>
    <input type="text" value="${question.category.name}" readonly>
    <br><br>

    <!-- Edit Choices -->
    <h3>Choices</h3>
    <c:forEach var="choice" items="${question.choices}" varStatus="status">
        <label for="choice${status.index + 1}">Choice ${status.index + 1}:</label><br>
        <input type="text" id="choice${status.index + 1}" name="choice${status.index + 1}"
               value="${choice.description}" required>
        <input type="radio" name="correctChoice" value="${status.index + 1}"
               <c:if test="${choice.correct}">checked</c:if>> Answer
        <br><br>
    </c:forEach>

    <!-- Submit Button -->
    <button type="submit">Save Changes</button>

    <!-- Cancel Button -->
    <button type="button" onclick="window.location.href='/admin/question-management'">Cancel</button>
</form>

</body>
</html>