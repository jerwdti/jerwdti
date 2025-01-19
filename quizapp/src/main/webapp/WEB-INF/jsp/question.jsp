<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>
<html>
<head>
    <title>Question ${position} of ${totalQuestions}</title>
</head>
<body>
<h1>Question ${position} of ${totalQuestions}</h1>

<!-- Navigation Bar -->
<nav>
    <h3>Navigate Questions:</h3>
    <c:forEach var="i" begin="1" end="${totalQuestions}">
        <a href="/quiz-question/${i}">Question ${i}</a> |
    </c:forEach>
</nav>

<hr>

<!-- Current Question -->
<div>
    <form method="post" action="/question/${position}">
        <h3>Question ${position}</h3>
        <p>${question.description}</p>

        <c:forEach items="${question.choices}" var="choice" varStatus="loop">
            <div>
                <input type="radio"
                       name="selectedChoiceId"
                       value="${choice.id}"/>
                    ${choice.description}
            </div>
        </c:forEach>

        <button type="submit">submit</button>
    </form>
</div>

<div>
    <c:if test="${position > 1}">
        <a href="/quiz-question/${position - 1}">Previous Question</a>
    </c:if>
    <c:if test="${position < totalQuestions}">
        <a href="/quiz-question/${position + 1}">Next Question</a>
    </c:if>
</div>

<!-- Submit Quiz Button -->
<div>
    <form method="post" action="/quiz-result/${quizId}">
        <button type="submit">Submit Quiz</button>
    </form>
</div>
</body>
</html>