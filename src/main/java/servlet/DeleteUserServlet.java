package servlet;

import model.User;
import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/delete")
public class DeleteUserServlet extends HttpServlet {
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("loginedUser", req.getAttribute("loginedUser"));
        System.out.println((User) req.getAttribute("loginedUser") + " in Class: "+ getClass().getCanonicalName());
        req.getRequestDispatcher("/editUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (userService.delUser(id)) {
            resp.setStatus(200);
        } else {
            resp.setStatus(403);
        }
        req.setAttribute("loginedUser", req.getAttribute("loginedUser"));
        req.getRequestDispatcher("/admin/all").forward(req, resp);
        System.out.println((User) req.getAttribute("loginedUser") + " in Class: "+ getClass().getCanonicalName());
//        resp.sendRedirect("/all");
    }
}
