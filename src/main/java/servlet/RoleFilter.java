package servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

public class RoleFilter implements Filter {
    private FilterConfig filterConfig;
    private static ArrayList<String> pages;

    public RoleFilter() {
        if (pages == null) {
            pages = new ArrayList<String>();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (filterConfig.getInitParameter("active").equalsIgnoreCase("true")) {
            HttpServletRequest req = (HttpServletRequest) request;
            String[] list = req.getRequestURI().split("/");
            String page = null;
            if (list[list.length - 1].indexOf(".jsp") > 0) {
                page = list[list.length - 1];
            }
            if (page != null && page.equalsIgnoreCase())
        }
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}
