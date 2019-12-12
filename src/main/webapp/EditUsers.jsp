<%--
  Created by IntelliJ IDEA.
  User: slava
  Date: 09.12.2019
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit user menu</title>
</head>

<body>
<p>Edit User<br>
    <p>Write id of user for delete:<br>
    <form action="/edit" method="post">
        <p>Id:<br>
            <input type="text" name="id">
        </p>
        <p>First name:<br>
            <input type="text" name="first_name">
        </p>
        <p>Last name:<br>
            <input type="text" name="last_name">
        </p>
        <p>Phone number<br>
            <input type="text" name="phone_number">
        </p>
        <input type="submit" value="Submit">
    </form>
    <br>
    <p>Delete user<br>
    <form action="/delete" method="post">
        <p>Write id of user for delete:<br>
            <input type="text" name="id">
        </p>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
