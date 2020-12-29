package dao;

import bean.Category;
import bean.Property;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO {

    /**
     * 获取分类下的属性总数，分页显示用到
     * @param cid
     * @return
     */
    public int getTotal(int cid) {
        int total = 0;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "select count(*) from Property where cid = " + cid;

            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(Property bean) {
        String sql = "insert into Property values(null, ?, ?)";
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, bean.getCategory().getId());
            ps.setString(2, bean.getName());
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
     * 修改分类属性
     * @param bean
     */
    public void update(Property bean) {
        String sql = "update Property set cid = ?, name = ? where id = ?";
        try (
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, bean.getCategory().getId());
            ps.setString(2, bean.getName());
            ps.setInt(3, bean.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除分类属性
     * @param id
     */
    public void delete(int id) {
        try (
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "delete from Property where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过id获取分类属性
     * @param id
     * @return
     */
    public Property get(int id) {
        Property bean = null;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "select * from Property where id =" + id;

            ResultSet rs = s.executeQuery(sql);

            if(rs.next()) {
                bean = new Property();
                String name = rs.getString("name");
                int cid = rs.getInt("cid");
                Category category = new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setName(name);
                bean.setCategory(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 获取cid分类下的所有属性
     * @param cid
     * @return
     */
    public List<Property> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

    public List<Property> list(int cid, int start, int count) {
        List<Property> beans = new ArrayList<Property>();

        String sql = "select * from Property where cid = ? order by id desc limit ?,?";

        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Property bean = new Property();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                Category category = new CategoryDAO().get(cid);
                bean.setId(id);
                bean.setName(name);
                bean.setCategory(category);

                beans.add(bean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

}
