<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
    <ul>
        <!-- Home Tab -->
        <c:if test="${not empty sessionScope.user}">
            <li><a href="/home">Home</a></li>
        </c:if>

        <!-- Taking Quiz Tab -->
        <c:if test="${not empty sessionScope.quiz}">
            <li><a href="/quiz-question/1">Taking Quiz</a></li>
        </c:if>

        <!-- Login/Logout Tab -->
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <li><a href="/logout">Logout</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="/login">Login</a></li>
            </c:otherwise>
        </c:choose>

        <!-- Register Tab -->
        <c:if test="${empty sessionScope.user}">
            <li><a href="/register">Register</a></li>
        </c:if>

        <!-- Contact Us Tab -->
        <li><a href="/contact">Contact Us</a></li>
    </ul>
</nav>