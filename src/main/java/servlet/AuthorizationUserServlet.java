package servlet;

import model.User;
import service.UserServiceImpl;
import util.AppUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authorization")
public class AuthorizationUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = UserServiceImpl.getInstance().checkAuth(login, password);

        if (user == null) {
            String errorMessage = "Invalid userName or Password";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
            return;
        }

        AppUtil.storeLoggedUser(req.getSession(),user);

        int redirectId = -1;
        try {
            redirectId = Integer.parseInt(req.getParameter("redirectId"));
        } catch (Exception e) {
            System.out.println("Redirect id is null");
        }
        String requestUri = AppUtil.getRedirectAfterLoginUrl(req.getSession(), redirectId);
        if (requestUri != null) {
            resp.sendRedirect(requestUri);
        } else {
            resp.sendRedirect(req.getContextPath() + "/user.jsp");
        }
    }
}
