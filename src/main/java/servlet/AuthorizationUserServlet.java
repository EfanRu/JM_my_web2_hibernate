package servlet;

import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import java.io.IOException;

@WebServlet("/authorization")
public class AuthorizationUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = UserServiceImpl.getInstance().checkAuth(login, password);

        if (user == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect("/authorization");
        } else if (user.getRole().equalsIgnoreCase("user")) {
            resp.setStatus(200);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
        } else if (user.getRole().equalsIgnoreCase("admin")) {
            resp.setStatus(200);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.sendRedirect("/authorization");
        }
    }
}
