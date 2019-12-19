package servlet;

import model.User;
import security.AccountManager;
import util.AppUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter("/admin/*")
public class RoleFilter implements Filter {
    private static ArrayList<String> pages;
    private AccountManager accMng = AccountManager.getInstance();

    public RoleFilter() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = req.getServletPath();

        User loginedUser = AppUtil.checkAuth(req.getParameter("login"), req.getParameter("password"));
        accMng.addUser(loginedUser, req.getSession());

        if (loginedUser == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect("/authorization");
            return;
        }

        if (!loginedUser.getRole().equalsIgnoreCase("admin")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
