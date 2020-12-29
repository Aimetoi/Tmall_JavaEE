package filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForeServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String contextPath = request.getSession().getServletContext().getContextPath();
        request.getSession().getServletContext().setAttribute("contextPath", contextPath);

        // 从Session对象获取用户信息、购物车信息等
//        User user = (User) request.getSession().getAttribute("user");
//        int cartTotalItemNumber = 0;
//        if(null != user) {
//            List<OrderItem> ois = new OrderItemDAO().listByUser(user.getId());
//            for(OrderItem oi : ois) {
//                cartTotalItemNumber += oi.getNumber();
//            }
//        }
//        request.setAttribute("cartTotalItemNumber", cartTotalItemNumber);

        // 简单搜索栏下显示分类链接
//        List<Category> cs = (List<Category>) request.getAttribute("cs");
//        if(null == cs) {
//            cs = new CategoryDAO().list();
//            request.setAttribute("cs", cs);
//        }

        String uri = request.getRequestURI();
        // 去除uri路径中的项目路径
        uri = StringUtils.remove(uri, contextPath);
        // 判断是否以/fore开头
        if(uri.startsWith("/fore") && !uri.startsWith("/foreServlet")) {
            String method = StringUtils.substringAfterLast(uri, "/fore");
            request.setAttribute("method", method);
            request.getRequestDispatcher("/foreServlet").forward(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
