<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Register</title>
</head>

<body>
<h1>Register</h1>

<form method="post" action="/register">
    <div>
        <label>First Name:</label>
        <input type="text" name="firstName" required>
    </div>
    <div>
        <label>Last Name:</label>
        <input type="text" name="lastName" required>
    </div>
    <div>
        <label>Email:</label>
        <input type="email" name="email" required>
    </div>
    <div>
        <label>Password:</label>
        <input type="password" name="password" required>
    </div>
    <button type="submit">Register</button>
</form>

<!-- Success Message -->
<div>
    <p style="color: green;">
        <%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %>
    </p>
</div>

<!-- Error message block -->
<div>
    <p style="color: red;">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>
</div>

<!-- Return to Login Page -->
<div>
    <p>Already have an account? <a href="/login">Return to Login Page</a></p>
</div>
</body>
</html>