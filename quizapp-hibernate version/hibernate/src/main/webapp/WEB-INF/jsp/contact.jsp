<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact Us</title>
</head>
<body>

<h1>Contact Us</h1>

<!-- Contact Form -->
<form action="/contact" method="post">
    <label for="subject">Subject:</label><br>
    <input type="text" id="subject" name="subject" required><br><br>

    <label for="email">Email:</label><br>
    <input type="email" id="email" name="email" required><br><br>

    <label for="message">Message:</label><br>
    <textarea id="message" name="message" rows="5" cols="50" required></textarea><br><br>

    <button type="submit">Send Message</button>
</form>

</body>
</html>