<%--
  Created by IntelliJ IDEA.
  User: slava
  Date: 17.12.2019
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome to your app piece of meat!</title>
</head>
<body>
    <p>Please log in:<br>
        <form action="/authorization" method="get">
            <p>Login:<br>
                <input type="text" name="login">
            </p>
            <p>Password:<br>
                <input type="password" name="password">
            </p>
            <input type="submit" value="Log in">
        </form>
    <br>
</body>
</html>
