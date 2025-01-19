<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>Contact Us - Result</title>
</head>
<body>
<h1>Contact Us</h1>

<c:choose>
    <c:when test="${success}">
        <p>Your message has been sent successfully!</p>
    </c:when>
    <c:otherwise>
        <p>There was an error sending your message. Please try again later.</p>
    </c:otherwise>
</c:choose>

<a href="/home">Go Back to Home</a>
</body>
</html>