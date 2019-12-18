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

@WebFilter("/*")
public class RoleFilter implements Filter {
    private FilterConfig filterConfig;
    private static ArrayList<String> pages;
    private AccountManager accMng = AccountManager.getInstance();

    public RoleFilter() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = req.getServletPath();
        HttpSession sess = req.getSession();

        if (servletPath.equalsIgnoreCase("/authorization")) {
            chain.doFilter(request, response);
            return;
        }

        User loginedUser = AppUtil.getLoginedUser(req.getSession());
        AppUtil.checkAuth(req.getParameter("login"), req.getParameter("password"));

        if (loginedUser != null) {
            String userName = loginedUser.getLogin();
            String role = loginedUser.getRole();
        }

        if (servletPath.contains("admin")) {
            if (loginedUser == null) {
                String reqUri = req.getRequestURI();
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.sendRedirect("/authorization");
                return;
            }

            if (!loginedUser.getRole().equalsIgnoreCase("admin")) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.sendRedirect("/authorization");
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
