<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.User" %>
<%@ page import="service.UserServiceImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.UserDaoImpl" %>
<%@ page import="servlets.AllUsersServlet" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Project</title>
  </head>

  <body>
  <p>It's your project! Welcome!<br>
  </body>

  <p>All users in database:<br>

      <form action="/all" method="get">
          <table>
              <tr>
                  <td>id</td>
                  <td>First name</td>
                  <td>Second name</td>
                  <td>Phone number</td>
              </tr>
                  <c:forEach var="user" items="${listUser}">
              <tr>
                      <td>${user.getId()}</td>
                      <td>${user.getFirstName()}</td>
                      <td>${user.getLastName()}</td>
                      <td>${user.getPhoneNumber()}</td>
              </tr>
                  </c:forEach>
          </table>
      </form>

  <body>
  <p>Add user:<br>
      <form action="/add" method="post">
          <p>First name:<br>
              <input type="text" name="firstName">
          </p>
          <p>Last name:<br>
              <input type="text" name="lastName">
          </p>
          <p>Phone number<br>
              <input type="text" name="phoneNumber">
          </p>
          <input type="submit" value="Submit">
      </form>
  </body>

  <body>
  <p>Edit and delete user:
      <form action="/edit" method="GET">
          <input type="submit" value="Edit or delete user">
      </form>
  </body>
</html>
