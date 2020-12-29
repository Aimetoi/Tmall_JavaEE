package servlet;

import bean.Category;
import bean.Product;
import bean.PropertyValue;
import util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Product add方法");

        Map<String, String> params = new HashMap<>();
        InputStream inputStream = super.parseUpload(request, params);
        System.out.println(params);

        int cid = Integer.parseInt(params.get("cid"));
        Category c = categoryDAO.get(cid);

        String name = params.get("name");
        String subTitle = params.get("subTitle");
        float originalPrice = Float.parseFloat(params.get("originalPrice"));
        float promotePrice = Float.parseFloat(params.get("promotePrice"));
        int stock = Integer.parseInt(params.get("stock"));

        Product p = new Product();

        p.setCategory(c);
        p.setName(name);
        p.setSubTitle(subTitle);
        p.setOriginalPrice(originalPrice);
        p.setPromotePrice(promotePrice);
        p.setStock(stock);
        p.setCreateDate(new Date());

        productDAO.add(p);
        return "@admin_product_list?cid=" + cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Product delete方法");

        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productDAO.get(id);
        productDAO.delete(id);
        return "@admin_product_list?cid=" + p.getCategory().getId();
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Product update方法");

        Map<String, String> params = new HashMap<>();
        InputStream inputStream = super.parseUpload(request, params);

        int cid = Integer.parseInt(params.get("cid"));
        Category c = categoryDAO.get(cid);

        int id = Integer.parseInt(params.get("id"));
        String name = params.get("name");
        String subTitle = params.get("subTitle");
        float originalPrice = Float.parseFloat(params.get("originalPrice"));
        float promotePrice = Float.parseFloat(params.get("promotePrice"));
        int stock = Integer.parseInt(params.get("stock"));

        Product p = new Product();

        p.setId(id);
        p.setName(name);
        p.setSubTitle(subTitle);
        p.setOriginalPrice(originalPrice);
        p.setPromotePrice(promotePrice);
        p.setStock(stock);
        p.setCategory(c);
        p.setCreateDate(new Date());

        productDAO.update(p);
        return "@admin_product_list?cid=" + p.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Product edit方法");

        int id =Integer.parseInt(request.getParameter("id"));
        Product p = productDAO.get(id);
        request.setAttribute("p",p);
        return "admin/editProduct.jsp";
    }

    public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page){
        System.out.println("调用了Product updatePropertyValue方法");

        int  pvid = Integer.parseInt(request.getParameter("pvid"));
        String value = request.getParameter("value");

        PropertyValue pv = propertyValueDAO.get(pvid);
        pv.setValue(value);

        propertyValueDAO.update(pv);

        return "%success";
    }

    public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page){
        System.out.println("调用了Product editPropertyValue方法");

        int id = Integer.parseInt(request.getParameter("id"));
        Product p = productDAO.get(id);
        request.setAttribute("p", p);

        // 初始化属性，将产品对应的属性全部
        propertyValueDAO.init(p);

        List<PropertyValue> pvs = propertyValueDAO.list(p.getId());

        request.setAttribute("pvs", pvs);

        return "admin/editPropertyValue.jsp";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了Product list方法");

        int cid = Integer.parseInt(request.getParameter("cid"));
        Category c = categoryDAO.get(cid);

        List<Product> ps = productDAO.list(cid, page.getStart(), page.getCount());

        int total = productDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid=" + c.getId());

        request.setAttribute("ps", ps);
        request.setAttribute("c", c);
        request.setAttribute("page", page);

        return "admin/listProduct.jsp";
    }
}
