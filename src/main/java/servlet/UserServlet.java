package servlet;

import bean.User;
import util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 UserServlet add方法");
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 UserServlet delete方法");

        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.delete(id);
        return "@admin_user_list";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 UserServlet update方法");

        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);
        System.out.println(params);

        int id = Integer.parseInt(params.get("id"));
        String password = params.get("password");

        User user = userDAO.get(id);
        user.setPassword(password);

        userDAO.update(user);

        return "@admin_user_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 UserServlet edit方法");

        int id = Integer.parseInt(request.getParameter("id"));
        User u = userDAO.get(id);

        request.setAttribute("u", u);

        return "admin/editUser.jsp";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 UserServlet list方法");

        List<User> us = userDAO.list(page.getStart(), page.getCount());
        int total = userDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("us", us);
        request.setAttribute("page", page);

        return "admin/listUser.jsp";
    }
}
