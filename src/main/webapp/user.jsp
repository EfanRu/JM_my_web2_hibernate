<%--
  Created by IntelliJ IDEA.
  User: slava
  Date: 17.12.2019
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>User's page</title>
</head>
<body>
<h3>Welcome to user page, piece of meat!</h3>
    Bla-bla-bla...
<form action="/user" method="get">
    <table>
        <tr>
            <td>id</td>
            <td>First name</td>
            <td>Second name</td>
            <td>Login</td>
            <td>Password</td>
            <td>Phone number</td>
            <td>Role</td>
        </tr>
            <tr>
                <td>${user.getId()}</td>
                <td>${user.getFirstName()}</td>
                <td>${user.getLastName()}</td>
                <td>${user.getLogin()}</td>
                <td>${user.getPassword()}</td>
                <td>${user.getPhoneNumber()}</td>
                <td>${user.getRole()}</td>
            </tr>
    </table>
</form>

    <form action="/logout" method="get">
        <input type="submit" value="Log out">
    </form>
</body>
</html>
