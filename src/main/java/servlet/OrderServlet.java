package servlet;

import bean.Order;
import dao.OrderDAO;
import util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public class OrderServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    public String delivery(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 OrderServlet delivery方法");

        int id = Integer.parseInt(request.getParameter("id"));
        Order o = orderDAO.get(id);
        o.setDeliveryDate(new Date());
        o.setStatus(OrderDAO.waitConfirm);
        orderDAO.update(o);
        return "@admin_order_list";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 OrderServlet list方法");

        // 对订单对应的订单项进行初始化
        List<Order> os = orderDAO.list(page.getStart(), page.getCount());
        orderItemDAO.fill(os);

        int total = orderDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("os", os);
        request.setAttribute("page", page);

        return "admin/listOrder.jsp";
    }
}
