<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Navigation Bar -->
<nav class="navbar">
    <div class="nav-container">
        <!-- Logo -->
        <a href="/" class="logo">QuizApp</a>

        <!-- Navigation Links -->
        <ul class="nav-links">
            <!-- Home Tab -->
            <c:if test="${not empty sessionScope.user}">
                <li><a href="/home" class="${pageContext.request.requestURI == '/home' ? 'active' : ''}">Home</a></li>
            </c:if>

            <!-- Taking Quiz Tab -->
            <c:if test="${not empty sessionScope.quiz}">
                <li><a href="/quiz-question/1" class="${pageContext.request.requestURI == '/quiz-question/1' ? 'active' : ''}">Taking Quiz</a></li>
            </c:if>

            <!-- Login/Logout Tab -->
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li><a href="/logout" onclick="return confirm('Are you sure you want to logout?');"
                           class="${pageContext.request.requestURI == '/logout' ? 'active' : ''}">Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="/login" class="${pageContext.request.requestURI == '/login' ? 'active' : ''}">Login</a></li>
                </c:otherwise>
            </c:choose>

            <!-- Register Tab -->
            <c:if test="${empty sessionScope.user}">
                <li><a href="/register" class="${pageContext.request.requestURI == '/register' ? 'active' : ''}">Register</a></li>
            </c:if>

            <!-- Contact Us Tab -->
            <li><a href="/contact" class="${pageContext.request.requestURI == '/contact' ? 'active' : ''}">Contact Us</a></li>
        </ul>
    </div>
</nav>

<style>

    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 20px;
        color: #333;
    }

    .navbar {
        background-color: #007bff;
        padding: 10px 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .navbar a.logo {
        color: #fff;
        font-size: 24px;
        text-decoration: none;
    }

    .navbar ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        display: flex;
    }

    .navbar ul li {
        margin-left: 20px;
    }

    .navbar ul li a {
        color: #fff;
        text-decoration: none;
        font-size: 16px;
    }

    .navbar ul li a:hover {
        text-decoration: underline;
    }

    button, .btn {
        padding: 10px 20px;
        font-size: 16px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    button:hover, .btn:hover {
        background-color: #0056b3;
    }

    input[type="text"], input[type="password"], input[type="email"] {
        width: 100%;
        padding: 10px;
        margin: 5px 0 15px 0;
        border: 1px solid #ccc;
        border-radius: 5px;
        box-sizing: border-box;
    }

    .form-container {
        width: 100%;
        max-width: 400px;
        margin: auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        text-align: center;
    }

</style>