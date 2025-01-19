<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Contact Management</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        .action-btn {
            padding: 5px 10px;
            text-decoration: none;
            color: white;
            background-color: #007BFF;
            border-radius: 4px;
        }

        .action-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h1>Contact Messages</h1>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Subject</th>
        <th>Email</th>
        <th>Message</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="message" items="${messages}">
        <tr>
            <td>${message.id}</td>
            <td>${message.subject}</td>
            <td>${message.email}</td>
            <td>${message.detail}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>