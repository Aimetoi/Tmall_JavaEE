package servlet;

import bean.Category;
import bean.Property;
import util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Property add方法");

        Map<String,String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);
        System.out.println(params);

        int cid = Integer.parseInt(params.get("cid"));
        Category c = categoryDAO.get(cid);

        String name = params.get("name");
        System.out.println(name);

        Property p = new Property();
        p.setCategory(c);
        p.setName(name);

        propertyDAO.add(p);

        return "@admin_property_list?cid=" + p.getCategory().getId();
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Property delete方法");

        int id = Integer.parseInt(request.getParameter("id"));
        Property p = propertyDAO.get(id);
        propertyDAO.delete(id);
        return "@admin_property_list?cid=" + p.getCategory().getId();
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Property update方法");

        Map<String,String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);
        System.out.println(params);

        int cid = Integer.parseInt(params.get("cid"));
        Category c = categoryDAO.get(cid);

        int id = Integer.parseInt(params.get("id"));
        String name = params.get("name");

        Property p = new Property();
        p.setId(id);
        p.setName(name);
        p.setCategory(c);

        propertyDAO.update(p);
        return "@admin_property_list?cid=" + p.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Property edit方法");

        int id = Integer.parseInt(request.getParameter("id"));
        Property p = propertyDAO.get(id);
        request.setAttribute("p", p);
        return "admin/editProperty.jsp";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Property list方法");

        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.get(cid);
        List<Property> ps = propertyDAO.list(cid, page.getStart(), page.getCount());

        int total = propertyDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid=" + c.getId());

        request.setAttribute("ps", ps);
        request.setAttribute("c", c);
        request.setAttribute("page", page);

        return "admin/listProperty.jsp";
    }
}
