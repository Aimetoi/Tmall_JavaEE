package servlet;

import dao.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseBackServlet extends HttpServlet {

    /* 添加 */
    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);
    /* 删除 */
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);
    /* 更新 */
    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);
    /* 修改 */
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);
    /* 显示 */
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);

    protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            /* 获取分页信息 */
            int start = 0;
            int count = 5;
            try{
                start = Integer.parseInt(req.getParameter("page.start"));
                count = Integer.parseInt(req.getParameter("page.count"));
            } catch(Exception e) {

            }

            Page page = new Page(start, count);

            /* 借助反射，调用对应的方法 */
            String method = (String)req.getAttribute("method");
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, Page.class);
            String redirect = m.invoke(this, req, resp, page).toString();

            /* 根据方法的返回值，进行相应的客户端、服务端跳转，或仅输出字符串 */
            if(redirect.startsWith("@")){
                resp.sendRedirect(redirect.substring(1));
            } else if(redirect.startsWith("%")){
                resp.getWriter().print(redirect.substring(1));
            } else {
                req.getRequestDispatcher(redirect).forward(req, resp);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params){
        InputStream is = null;
        try{
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            //设置上传的文件大小限制为10M
            factory.setSizeThreshold(1024 * 1024);

            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            while(iter.hasNext()){
                FileItem item = (FileItem) iter.next();
                if(!item.isFormField()){
                    //获取上传文件的输入流
                    is = item.getInputStream();
                } else {
                    String paramName = item.getFieldName();
                    String paramValue = item.getString();
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    params.put(paramName, paramValue);
                }
            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

}
