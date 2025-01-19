<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Management</title>
</head>
<body>
<h1>User Management</h1>
<c:if test="${not empty successMessage}">
    <div>${successMessage}</div>
</c:if>
<table border="1">
    <thead>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Email</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.active ? "Active" : "Suspended"}</td>
            <td>
                <form method="post" action="/admin/user-management/toggle-status">
                    <input type="hidden" name="userId" value="${user.id}">
                    <button type="submit">${user.active ? "Suspend" : "Activate"}</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Pagination Controls -->
<div>
    <c:if test="${currentPage > 1}">
        <a href="/admin/user-management?page=${currentPage - 1}">Previous</a>
    </c:if>
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="/admin/user-management?page=${i}">Page ${i}</a>
    </c:forEach>
    <c:if test="${currentPage < totalPages}">
        <a href="/admin/user-management?page=${currentPage + 1}">Next</a>
    </c:if>
</div>

<div>
    <a href="/admin">Return Admin Page</a>
</div>


</body>
</html>