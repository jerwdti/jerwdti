<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>

<html>
<head>
    <title>Register</title>
    <style>

        /* Global Styles */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
            color: #333;
        }

        /* Navigation Bar */
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

        /* Buttons */
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

        /* Form Inputs */
        input[type="text"], input[type="password"], input[type="email"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        /* Form Container */
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
</head>

<body>
<h1>Register</h1>

<form method="post" action="/register">
    <div>
        <label>First Name:</label>
        <input type="text" name="firstName" required>
    </div>
    <div>
        <label>Last Name:</label>
        <input type="text" name="lastName" required>
    </div>
    <div>
        <label>Email:</label>
        <input type="email" name="email" required>
    </div>
    <div>
        <label>Password:</label>
        <input type="password" name="password" required>
    </div>
    <button type="submit">Register</button>
</form>

<!-- Success Message -->
<div>
    <p style="color: green;">
        <%= request.getAttribute("success") != null ? request.getAttribute("success") : "" %>
    </p>
</div>

<!-- Error message block -->
<div>
    <p style="color: red;">
        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
    </p>
</div>

<!-- Return to Login Page -->
<div>
    <p>Already have an account? <a href="/login">Return to Login Page</a></p>
</div>
</body>
</html>