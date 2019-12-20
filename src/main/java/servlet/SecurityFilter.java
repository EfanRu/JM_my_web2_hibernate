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
public class SecurityFilter implements Filter {
    private static ArrayList<String> pages;
    private AccountManager accMng = AccountManager.getInstance();

    public SecurityFilter() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = req.getServletPath();
        User loginedUser;
        User buff;

        if ((buff = (User) req.getAttribute("loginedUser")) != null) {
            loginedUser = buff;
        } else if ((buff = AppUtil.checkAuth(req.getParameter("login"), req.getParameter("password"))) != null) {
            loginedUser = buff;
            accMng.addUser(loginedUser, req.getSession());
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.sendRedirect("/authorization");
            return;
        }
        System.out.println((User) req.getAttribute("loginedUser") + " in Class: "+ getClass().getCanonicalName());

        if (!loginedUser.getRole().equalsIgnoreCase("admin")) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("loginedUser", loginedUser);
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
