package servlet;

import bean.Category;
import util.ImageUtil;
import util.Page;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了category add方法");

        Map<String,String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        System.out.println(params);
        String name = params.get("name");
        Category c = new Category();
        c.setName(name);
        categoryDAO.add(c);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");

        try{
            if(null != is && 0 != is.available()){
                try(
                        FileOutputStream fos = new FileOutputStream(file);
                        ){
                    byte[] b = new byte[1024 * 1024];
                    int length = 0;
                    while(-1 != (length = is.read(b))){
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    //通过以下代码，将文件保存为jsp格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了category delete方法");
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        return "@admin_category_list";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了category update方法");

        Map<String,String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        System.out.println(params);
        String name = params.get("name");
        int id = Integer.parseInt(params.get("id"));

        Category c = new Category();
        c.setName(name);
        c.setId(id);
        categoryDAO.update(c);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");

        try{
            if(null != is && 0 != is.available()){
                try(
                        FileOutputStream fos = new FileOutputStream(file);
                ){
                    byte[] b = new byte[1024 * 1024];
                    int length = 0;
                    while(-1 != (length = is.read(b))){
                        fos.write(b, 0, length);
                    }
                    fos.flush();
                    //通过以下代码，将文件保存为jsp格式
                    BufferedImage img = ImageUtil.change2jpg(file);
                    ImageIO.write(img, "jpg", file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "@admin_category_list";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了category edit方法");

        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryDAO.get(id);
        request.setAttribute("c", category);
        return "admin/editCategory.jsp";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> cs = categoryDAO.list(page.getStart(), page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs", cs);
        request.setAttribute("page", page);

        return "admin/listCategory.jsp";
    }
}
