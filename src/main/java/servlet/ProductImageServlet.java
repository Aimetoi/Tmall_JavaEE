package servlet;

import bean.Product;
import bean.ProductImage;
import dao.ProductImageDAO;
import util.ImageUtil;
import util.Page;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductImageServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ProductImageServlet add方法");

        // 解析上传的数据
        Map<String, String> params = new HashMap<>();
        InputStream is = super.parseUpload(request, params);

        // 根据上传的参数生成对应的ProductImage对象
        String type = params.get("type");
        int pid = Integer.parseInt(params.get("pid"));
        Product p = productDAO.get(pid);

        // 生成产品图片对象
        ProductImage pi = new ProductImage();
        pi.setType(type);
        pi.setProduct(p);
        productImageDAO.add(pi);

        // 生成文件
        String filename = pi.getId() + ".jpg";
        String imageFolder = null;
        String imageFolder_small = null;
        String imageFolder_middle = null;

        if(productImageDAO.type_single.equals(pi.getType())){
            imageFolder = request.getSession().getServletContext().getRealPath("img/productSingle");
            imageFolder_small = request.getSession().getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");
        } else {
            imageFolder = request.getSession().getServletContext().getRealPath("img/productDetail");
        }

        File f = new File(imageFolder, filename);
        f.getParentFile().mkdirs();

        // 复制文件
        try{
            if(null != is && 0 != is.available()){
                try(
                        FileOutputStream fos = new FileOutputStream(f);
                        ){
                    byte[] b = new byte[1024 * 1024];
                    int length = 0;

                    while(-1 != (length = is.read(b))){
                        fos.write(b, 0, length);
                    }
                    fos.flush();

                    //将文件保存为jpg格式
                    BufferedImage img = ImageUtil.change2jpg(f);
                    ImageIO.write(img, "jpg", f);

                    if(ProductImageDAO.type_single.equals(pi.getType())){
                        File f_small = new File(imageFolder_small, filename);
                        File f_middle = new File(imageFolder_middle, filename);

                        ImageUtil.resizeImage(f, 56, 56, f_small);
                        ImageUtil.resizeImage(f, 217, 190, f_middle);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return "@admin_productImage_list?pid=" + p.getId();
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ProductImageServlet delete方法");

        int id = Integer.parseInt(request.getParameter("id"));
        ProductImage pi = productImageDAO.get(id);
        productImageDAO.delete(id);

        if(productImageDAO.type_single.equals(pi.getType())){
            String imageFolder_single = request.getSession().getServletContext().getRealPath("img/productSingle");
            String imageFolder_small =  request.getSession().getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle = request.getSession().getServletContext().getRealPath("img/productSingle_middle");

            File f_single = new File(imageFolder_single, pi.getId() + ".jpg");
            File f_small = new File(imageFolder_small, pi.getId() + ".jpg");
            File f_middle = new File(imageFolder_middle, pi.getId() + ".jpg");
            f_single.delete();
            f_small.delete();
            f_middle.delete();
        } else {
            String imageFolder_detail = request.getSession().getServletContext().getRealPath("img/productDetail");
            File f_detail = new File(imageFolder_detail, pi.getId() + ".jpg");
            f_detail.delete();
        }

        return "@admin_productImage_list?pid=" + pi.getProduct().getId();
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ProductImageServlet update方法");
        return "";
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ProductImageServlet edit方法");
        return "";
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        System.out.println("调用了 ProductImageServlet list方法");

        int pid = Integer.parseInt(request.getParameter("pid"));
        Product p = productDAO.get(pid);

        List<ProductImage> pisSingle = productImageDAO.list(p, ProductImageDAO.type_single);
        List<ProductImage> pisDetail = productImageDAO.list(p, ProductImageDAO.type_detail);

        request.setAttribute("p", p);
        request.setAttribute("pisSingle", pisSingle);
        request.setAttribute("pisDetail", pisDetail);

        return "admin/listProductImage.jsp";
    }
}
