package filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BackServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String contextPath = req.getSession().getServletContext().getContextPath();
        String uri = req.getRequestURI();
        //去除uri路径中的项目路径
        uri = StringUtils.remove(uri, contextPath);
        //判断是否为admin_开头
        if(uri.startsWith("/admin_")) {
            String servletPath = StringUtils.substringBetween(uri, "_", "_") +
                    "Servlet";
            String method = StringUtils.substringAfterLast(uri, "_");
            req.setAttribute("method", method);
            req.getRequestDispatcher("/" + servletPath).forward(req, resp);
            return;
        }

        filterChain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
