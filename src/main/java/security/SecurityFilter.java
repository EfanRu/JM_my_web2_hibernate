package security;

import model.User;
import service.UserServiceImpl;
import util.AppUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    public SecurityFilter() {}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        UserServiceImpl.getInstance().addUser(new User("Admin", "Root", "admin", "admin", 0l, "admin"));
        UserServiceImpl.getInstance().addUser(new User("User_1", "Test", "user", "user", 0l, "user"));
        UserServiceImpl.getInstance().addUser(new User("User_2", "Test", "user2", "user2", 0l, "user"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = req.getServletPath();

        if (servletPath.equals("/authorization") ||
            servletPath.equals("/login") ||
            servletPath.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        if (servletPath.contains(".jsp")) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/login");
            dispatcher.forward(request, response);
            return;
        }

        User loginedUser = AppUtil.getLoginedUser(req.getSession());
        HttpServletRequest wrapRequest = req;

        if (loginedUser != null) {
            String role = loginedUser.getRole();
            wrapRequest = new UserRoleRequestWrapper(role, req);
        }

        if (SecurityUtils.isSecurityPage(req)) {
            if (loginedUser == null) {
                resp.sendRedirect( "/login");
                return;
            }

            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                resp.sendRedirect( "/login");
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
