package servlet;

import service.UserService;
import service.UserServiceImpl;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/add")
public class AddUserServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phoneNum = req.getParameter("phoneNumber");
        String role = req.getParameter("role");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setStatus(userService.addUser(new User(firstName, lastName, login, password, Long.parseLong(phoneNum), role)) ? 200 : 403);
        resp.sendRedirect("/admin/all");
    }
}
