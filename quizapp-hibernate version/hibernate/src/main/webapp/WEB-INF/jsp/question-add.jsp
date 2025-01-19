<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Add New Question</title>
</head>
<body>

<h1>Add New Question</h1>

<!-- Error Message -->
<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>

<!-- Form to Add a New Question -->
<form action="/admin/question-add" method="post">

    <!-- Question Description -->
    <label for="description">Question:</label><br>
    <textarea id="description" name="description" rows="4" cols="50" required></textarea><br><br>

    <!-- Category Dropdown -->
    <label for="category">Category:</label><br>
    <select id="category" name="categoryId" required>
        <option value="">-- Select Category --</option>
        <c:forEach var="category" items="${categories}">
            <option value="${category.id}">${category.name}</option>
        </c:forEach>
    </select><br><br>

    <!-- Answer Choices -->
    <label>Choices:</label><br>
    <c:forEach var="i" begin="1" end="3">
        <input type="text" name="choice${i}" placeholder="Choice ${i}" required />
        <input type="radio" name="correctChoice" value="${i}" required /> Correct<br>
    </c:forEach>

    <br>
    <button type="submit">Add Question</button>
</form>

<!-- Back to Question Management -->
<br>
<a href="/admin/question-management">Back to Question Management</a>

</body>
</html>