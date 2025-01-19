<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="navbar.jsp" %>

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

        <!-- Display answer choices as radio buttons -->
        <c:forEach items="${choices}" var="choice">
            <div class="choice <c:if test='${choice.id == selectedChoiceId}'>selected</c:if>">
                <input type="radio"
                       name="selectedChoice"
                       value="${choice.id}"
                       <c:if test="${choice.id == selectedChoiceId}">checked</c:if> />
                    ${choice.description}
            </div>
        </c:forEach>

        <button type="submit">Submit Answer</button>
    </form>
</div>

<!-- Navigation between questions -->
<div class="nav-links">
    <c:if test="${position > 1}">
        <a href="/quiz-question/${position - 1}">Previous Question</a>
    </c:if>

    <c:if test="${position < totalQuestions}">
        <a href="/quiz-question/${position + 1}">Next Question</a>
    </c:if>
</div>

<!-- Submit the entire quiz without JavaScript -->
<div>
    <form method="post" action="/quiz-result/${quiz.id}">
        <input type="hidden" name="quizId" value="${quiz.id}" />
        <button type="submit" class="submit-quiz-btn">Submit Quiz</button>
    </form>
</div>

</body>
</html>