<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Login</title>
</head>

<body>
<h1>Login</h1>
<div>
    <form method="post" action="/login">
        <div>
            <label>Email</label>
            <input type="text" name="email" placeholder="Enter your email">
        </div>
        <div>
            <label>Password</label>
            <input type="password" name="password" placeholder="Enter your password">
        </div>
        <button type="submit">Submit</button>
    </form>

    <!-- Success message block -->
    <div>
        <c:if test="${not empty success}">
            <p style="color: green;">${success}</p>
        </c:if>
    </div>

    <!-- Error message block -->
    <div>
        <c:if test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:if>
    </div>

    <div>
        <p>Don't have an account? <a href="/register">Register here</a></p>
    </div>
</div>
</body>
</html>