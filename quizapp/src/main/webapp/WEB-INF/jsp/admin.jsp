<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Admin Dashboard</title>
</head>
<body>
<h1>Admin Dashboard</h1>

<div>
    <h2>Management Pages</h2>
    <ul>
        <li>
            <a href="/admin/user-management">User Management Page</a>
        </li>
        <li>
            <a href="/admin/quiz-management">Quiz Result Management Page</a>
        </li>
        <li>
            <a href="/admin/question-management">Question Management Page</a>
        </li>
        <li>
            <a href="/admin/contact-management">Contact Us Management Page</a>
        </li>
    </ul>
</div>

<div>
    <a href="/logout">Logout</a>
</div>
</body>
</html>