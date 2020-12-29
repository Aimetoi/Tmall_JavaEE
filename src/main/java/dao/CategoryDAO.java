package dao;

import bean.Category;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    /**
     * 获取分类的总数
     * @return
     */
    public int getTotal() {
        int total = 0;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
                ) {
            String sql = "select count(*) from Category";

            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * 增加分类
     * @param bean
     */
    public void add(Category bean) {
        String sql = "insert into category values(null, ?)";
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ) {

            ps.setString(1, bean.getName());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改
     * @param bean
     */
    public void update(Category bean) {
        String sql = "update category set name = ? where id = ?";
        try (
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setString(1, bean.getName());
            ps.setInt(2, bean.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(int id) {
        try (
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "delete from Category where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过id获取分类信息
     * @param id
     * @return
     */
    public Category get(int id) {
        Category bean = null;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
                ) {
            String sql = "select * from Category where id =" + id;

            ResultSet rs = s.executeQuery(sql);

            if(rs.next()) {
                bean = new Category();
                String name = rs.getString(2);
                bean.setId(id);
                bean.setName(name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 返回全部分类数据
     * @return
     */
    public List<Category> list() {
        return list(0, Short.MAX_VALUE);
    }

    /**
     * 返回区间内的分类数据
     * @param start
     * @param count
     * @return
     */
    public List<Category> list(int start, int count) {
        List<Category> beans = new ArrayList<Category>();

        String sql = "select * from Category order by id desc limit ?,?";

        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
                ) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Category bean = new Category();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                bean.setId(id);
                bean.setName(name);
                beans.add(bean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

}
