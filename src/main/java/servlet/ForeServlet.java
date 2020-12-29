package servlet;

import bean.*;
import comparator.*;
import dao.CategoryDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import dao.ProductImageDAO;
import util.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ForeServlet extends BaseForeServlet {

    /**
     * 显示主页
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ForeServlet home方法");

        List<Category> cs = new CategoryDAO().list();
        new ProductDAO().fill(cs);
        new ProductDAO().fillByRow(cs);
        request.setAttribute("cs", cs);
        return "home.jsp";
    }

    /**
     * 注册
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ForeServlet register方法");

        Map<String, String> params = new HashMap<>();
        InputStream inputStream = super.parseUpload(request, params);
        System.out.println(params);

        String name = params.get("name");
        String password = params.get("password");
        System.out.println(name);

        // 判断用户名是否存在
        boolean exist = userDAO.isExists(name);
        if (exist) {
            request.setAttribute("msg", "用户名已经被使用");
            return "register.jsp";
        }

        User user = new User();
        user.setName(name);
        user.setPassword(password);
        System.out.println(user.getName());
        System.out.println(user.getPassword());

        userDAO.add(user);

        return "@registerSuccess.jsp";
    }

    /**
     * 登录
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ForeServlet login方法");

        Map<String, String> params = new HashMap<>();
        InputStream inputStream = super.parseUpload(request, params);
        System.out.println(params);

        String name = params.get("name");
        String password = params.get("password");

        // 登录
        User user = userDAO.get(name, password);

        if (null == user) {
            request.setAttribute("msg", "账号密码错误");
            return "login.jsp";
        }

        // 将账号密码储存在Session中
        request.getSession().setAttribute("user", user);
        return "@forehome";
    }

    /**
     * 登出
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ForeServlet logout方法");

        request.getSession().removeAttribute("user");
        return "@forehome";
    }

    /**
     * 检查用户是否登录
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            return "%success";
        }
        return "%fail";
    }

    /**
     * 模态登录框通过 Ajax 方法登录
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        User user = userDAO.get(name, password);
        if (user == null)
            return "%fail";

        request.getSession().setAttribute("user", user);
        return "%success";
    }

    /**
     * 显示产品页
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ForeServlet product方法");

        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productDAO.get(pid);

        // 获取产品的single图片和detail图片
        List<ProductImage> productSingleImages = productImageDAO.list(p, ProductImageDAO.type_single);
        List<ProductImage> productDetailImages = productImageDAO.list(p, ProductImageDAO.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        //p.setFirstProductImage(productSingleImages.get(0));

        // 获取产品属性信息
        List<PropertyValue> pvs = propertyValueDAO.list(p.getId());

        // 获取产品评价信息
        List<Review> reviews = reviewDAO.list(p.getId());

        // 设置产品销量数和评价数
        productDAO.setSaleAndReviewNumber(p);

        request.setAttribute("p", p);
        request.setAttribute("pvs", pvs);
        request.setAttribute("reviews", reviews);

        return "product.jsp";
    }

    /**
     * 分类页面
     *
     * @return
     */
    public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet category方法");

        int cid = Integer.parseInt(request.getParameter("cid"));

        // 获取分类对象并为填充产品对象、销量、评价
        Category c = new CategoryDAO().get(cid);
        new ProductDAO().fill(c);
        new ProductDAO().setSaleAndReviewNumber(c.getProducts());

        // 根据获取的排序方式进行排序
        String sort = request.getParameter("sort");
        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(c.getProducts(), new ProductAllComparator());
                    break;
            }
        }

        // 排序完成后，将分类对象添加到域对象 request 中
        request.setAttribute("c", c);
        return "category.jsp";
    }

    /**
     * 搜索功能
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String search(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet search方法");

        String keyword = request.getParameter("keyword");
        List<Product> ps = null;

        // 加入异常，防止崩溃
        try {
            ps = productDAO.search(keyword, 0, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 设置产品的销量和评论
        productDAO.setSaleAndReviewNumber(ps);

        request.setAttribute("ps", ps);
        return "searchResult.jsp";
    }

    /**
     * 立即购买
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String buyone(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet buyone方法");

        int pid = Integer.parseInt(request.getParameter("pid"));
        int num = Integer.parseInt(request.getParameter("num"));

        Product p = productDAO.get(pid);
        User user = (User) request.getSession().getAttribute("user");
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());

        int oiid = 0;
        boolean found = false;

        // 遍历订单，如果订单已存在对应的产品，就在对应购物车的基础上调整数量
        if (null != ois) {
            for (OrderItem oi : ois) {
                if (oi.getProduct().getId() == p.getId()) {
                    oi.setNumber(oi.getNumber() + num);
                    orderItemDAO.update(oi);

                    found = true;
                    oiid = oi.getId();
                    break;
                }
            }
        }

        // 如果没找到对应的商品，则新建一条订单项
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setProduct(p);
            oi.setNumber(num);
            orderItemDAO.add(oi);
            oiid = oi.getId();
        }

        return "@forebuy?oiid=" + oiid;
    }

    /**
     * 结算页面
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String buy(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet buy方法");

        String[] oiids = request.getParameterValues("oiid");
        List<OrderItem> ois = new ArrayList<>();
        float total = 0;

        for (String strid : oiids) {
            int oiid = Integer.parseInt(strid);
            OrderItem oi = orderItemDAO.get(oiid);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            ois.add(oi);
        }

        request.getSession().setAttribute("ois", ois);
        request.setAttribute("total", total);
        return "buy.jsp";
    }

    /**
     * 购物车
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet cart方法");

        User user = (User) request.getSession().getAttribute("user");
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        request.setAttribute("ois", ois);
        return "cart.jsp";
    }

    /**
     * 加入购物车
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet addCart方法");

        int pid = Integer.parseInt(request.getParameter("pid"));
        int num = Integer.parseInt(request.getParameter("num"));

        Product p = productDAO.get(pid);
        User user = (User) request.getSession().getAttribute("user");
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());

        int oiid = 0;
        boolean found = false;

        // 遍历订单，如果订单已存在对应的产品，就在对应购物车的基础上调整数量
        if (null != ois) {
            for (OrderItem oi : ois) {
                if (oi.getProduct().getId() == p.getId()) {
                    oi.setNumber(oi.getNumber() + num);
                    orderItemDAO.update(oi);

                    found = true;
                    oiid = oi.getId();
                    break;
                }
            }
        }

        // 如果没找到对应的商品，则新建一条订单项
        if (!found) {
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setProduct(p);
            oi.setNumber(num);
            orderItemDAO.add(oi);
            oiid = oi.getId();
        }

        return "%success";
    }

    /**
     * 更改当前订单项的内容
     *
     * @param request
     * @param response
     * @param page
     * @return
     * @throws UnsupportedEncodingException
     */
    public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {
        System.out.println("调用了ForeServlet changeOrderItem方法");

        User user = (User) request.getSession().getAttribute("user");
        if (null == user)
            return "%fail";

        int pid = Integer.parseInt(request.getParameter("pid"));
        int number = Integer.parseInt(request.getParameter("number"));
        List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == pid) {
                oi.setNumber(number);
                orderItemDAO.update(oi);
                break;
            }
        }
        return "%success";
    }

    /**
     * 删除订单项
     *
     * @param request
     * @param response
     * @param page
     * @return
     * @throws UnsupportedEncodingException
     */
    public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {
        System.out.println("调用了ForeServlet deleteOrderItem方法");

        User user = (User) request.getSession().getAttribute("user");
        if (null == user)
            return "%fail";

        int oiid = Integer.parseInt(request.getParameter("oiid"));
        orderItemDAO.delete(oiid);
        return "%success";
    }

    /**
     * 创建订单
     *
     * @param request
     * @param response
     * @param page
     * @return
     * @throws UnsupportedEncodingException
     */
    public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page) throws UnsupportedEncodingException {
        System.out.println("调用了ForeServlet createOrder方法");

        // 防止post方法中文乱码
        request.setCharacterEncoding("UTF-8");

        User user = (User) request.getSession().getAttribute("user");

        List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");
        if (ois.isEmpty())
            return "@login.jsp";

        String address = request.getParameter("address");
        String post = request.getParameter("post");
        String reciver = request.getParameter("reciver");
        String mobile = request.getParameter("mobile");

        Order order = new Order();
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        order.setOrderCode(orderCode);
        order.setAddress(address);
        order.setPost(post);
        order.setReceiver(reciver);
        order.setMobile(mobile);
        order.setUserMessage("默认内容");
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderDAO.waitPay);

        orderDAO.add(order);

        // 统计总金额并设置每个订单项的所属订单
        float total = 0;
        for (OrderItem oi : ois) {
            oi.setOrder(order);
            orderItemDAO.update(oi);

            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }

        return "@forealipay?oid=" + order.getId() + "&total=" + total;
    }

    /**
     * 支付界面
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String alipay(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet alipay方法");

        return "alipay.jsp";
    }

    /**
     * 支付完成界面
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String payed(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet payed方法");

        int oid = Integer.parseInt(request.getParameter("oid"));
        Order order = orderDAO.get(oid);

        order.setStatus(OrderDAO.waitDelivery);
        order.setPayDate(new Date());
        orderDAO.update(order);

        request.setAttribute("o", order);
        return "payed.jsp";
    }

    /**
     * 我的订单页面
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String bought(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了ForeServlet bought方法");

        User user = (User) request.getSession().getAttribute("user");
        List<Order> os = orderDAO.list(user.getId(), OrderDAO.delete);

        orderItemDAO.fill(os);

        request.setAttribute("os", os);
        return "bought.jsp";
    }



}