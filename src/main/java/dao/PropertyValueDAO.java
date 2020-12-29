package dao;

import bean.Product;
import bean.Property;
import bean.PropertyValue;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyValueDAO {

    public int getTotal() {
        int total = 0;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "select count(*) from PropertyValue";

            ResultSet rs = s.executeQuery(sql);
            while(rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void add(PropertyValue bean) {
        String sql = "insert into PropertyValue values(null, ?, ?, ?)";
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());
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

    public void update(PropertyValue bean) {
        String sql = "update PropertyValue set pid = ?, ptid = ?, value = ? where id = ?";
        try (
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, bean.getProduct().getId());
            ps.setInt(2, bean.getProperty().getId());
            ps.setString(3, bean.getValue());
            ps.setInt(4, bean.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "delete from PropertyValue where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PropertyValue get(int id) {
        PropertyValue bean = null;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "select * from PropertyValue where id =" + id;

            ResultSet rs = s.executeQuery(sql);

            if(rs.next()) {
                bean = new PropertyValue();
                int pid = rs.getInt("pid");
                Product product = new ProductDAO().get(pid);
                int ptid = rs.getInt("ptid");
                Property property = new PropertyDAO().get(ptid);
                String value = rs.getString("value");

                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public PropertyValue get(int ptid, int pid) {
        String sql = "select * from PropertyValue where pid = ? and ptid = ?";

        PropertyValue bean = null;
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, pid);
            ps.setInt(2, ptid);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                bean = new PropertyValue();
                int id = rs.getInt("id");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);

                bean.setId(id);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public List<PropertyValue> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<PropertyValue> list(int start, int count) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();

        String sql = "select * from PropertyValue order by id desc limit ?,?";

        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);

                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);

                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);

                beans.add(bean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public void init(Product p) {
        List<Property> pts = new PropertyDAO().list(p.getCategory().getId());

        for(Property pt : pts) {
            PropertyValue pv = get(pt.getId(), p.getId());
            if(null == pv) {
                pv = new PropertyValue();
                pv.setProduct(p);
                pv.setProperty(pt);
                this.add(pv);
            }
        }
    }

    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<PropertyValue>();

        String sql = "select * from PropertyValue where pid = ? order by ptid desc";

        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, pid);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);

                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");

                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);

                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);

                beans.add(bean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    public static void main(String[] args) {
        PropertyValueDAO dao = new PropertyValueDAO();
        PropertyValue pv = dao.get(9, 7);
        System.out.println(pv.getValue());
    }

}
