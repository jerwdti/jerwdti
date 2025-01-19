<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact Result</title>
</head>
<body>

<c:if test="${success}">
    <h2>Your message was sent successfully!</h2>
    <%-- Link back to contact form or home --%>
    <a href="/contact">Send Another Message</a>
</c:if>

<c:if test="${!success}">
    <h2 style="color:red;">Failed to send your message. Please try again.</h2>
    <a href="/contact">Try Again</a>
</c:if>

</body>
</html>