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
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = req.getServletPath();

        if (servletPath.equals("/authorization")) {
            chain.doFilter(request, response);
            return;
        }

        User loginedUser = AppUtil.getLoginedUser(req.getSession());
        HttpServletRequest wrapRequest = req;

        if (loginedUser != null) {
            String userLogin = loginedUser.getLogin();
            String role = loginedUser.getRole();
            wrapRequest = new UserRoleRequestWrapper(userLogin, role, req);
        }

        if (SecurityUtils.isSecurityPage(req)) {
            if (loginedUser == null) {
                String requestUri = req.getRequestURI();
                int redirectId = AppUtil.storeRedirectAfterLoginUrl(req.getSession(), requestUri);

                resp.sendRedirect(wrapRequest.getContextPath() + "/");
                return;
            }

            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/authorization");
                dispatcher.forward(request, response);
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}
}
