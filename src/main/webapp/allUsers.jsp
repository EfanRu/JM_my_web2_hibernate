<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Project</title>
  </head>

  <body>
  <p>It's your project! Welcome!<br>
  </body>

  <p>All users in database:<br>

      <form action="/admin/all" method="get">
          <table>
              <tr>
                  <td>id</td>
                  <td>First name</td>
                  <td>Second name</td>
                  <td>Login</td>
                  <td>Password</td>
                  <td>Phone number</td>
                  <td>Role</td>
                  <td>Edit</td>
                  <td>Remove</td>
              </tr>
                  <c:forEach var="user" items="${listUser}">
              <tr>
                      <td>${user.getId()}</td>
                      <td>${user.getFirstName()}</td>
                      <td>${user.getLastName()}</td>
                      <td>${user.getLogin()}</td>
                      <td>${user.getPassword()}</td>
                      <td>${user.getPhoneNumber()}</td>
                      <td>${user.getRole()}</td>
                      <td>
                          <form action="/admin/edit" method="get">
                              <button type="submit" name="id" value="${user.getId()}">Edit</button>
                          </form>
                      </td>
                      <td>
                          <form action="/admin/delete" method="post">
                              <button type="submit" name="id" value="${user.getId()}">Del</button>
                          </form>
                      </td>

              </tr>
                  </c:forEach>
          </table>
      </form>

  <body>
  <p>Add user:<br>
      <form action="/admin/add" method="post">
          <p>First name:<br>
              <input type="text" name="firstName">
          </p>
          <p>Last name:<br>
              <input type="text" name="lastName">
          </p>
          <p>Phone number<br>
              <input type="text" name="phoneNumber">
          </p>
            <p>Role<br>
              <input type="text" name="role">
          </p>
          </p>
          <p>Login<br>
              <input type="text" name="login">
          </p>
          </p>
          <p>Password<br>
              <input type="password" name="password">
          </p>
          <input type="submit" value="Submit">
      </form>
  </body>

<%--
  <body>
  <p>Edit and delete user:
      <form action="/admin/edit" method="GET">
          <input type="submit" value="Edit or delete user">
      </form>
--%>


    <form action="/logout" method="get">
      <input type="submit" value="Log out">
    </form>
  </body>
</html>
