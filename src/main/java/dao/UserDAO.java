package dao;

import bean.User;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    /**
     * 获取用户总数
     * @return
     */
    public int getTotal() {
        int total = 0;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "select count(*) from User";

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
     * 添加用户信息
     * @param bean
     */
    public void add(User bean) {
        String sql = "insert into User values(null, ?, ?)";
        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
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
     * 更新用户信息
     * @param bean
     */
    public void update(User bean) {
        String sql = "update User set name = ?, password = ? where id = ?";
        try (
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.setInt(3, bean.getId());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除用户信息
     * @param id
     */
    public void delete(int id) {
        try (
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "delete from User where id = " + id;

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过id获取用户信息
     * @param id
     * @return
     */
    public User get(int id) {
        User bean = null;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "select * from User where id =" + id;

            ResultSet rs = s.executeQuery(sql);

            if(rs.next()) {
                bean = new User();
                String name = rs.getString(2);
                String password = rs.getString(3);
                bean.setId(id);
                bean.setName(name);
                bean.setPassword(password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 显示所有用户记录
     * @return
     */
    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<User> list(int start, int count) {
        List<User> beans = new ArrayList<User>();

        String sql = "select * from User order by id desc limit ?,?";

        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setInt(1, start);
            ps.setInt(2, count);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                User bean = new User();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String password = rs.getString(3);
                bean.setId(id);
                bean.setName(name);
                bean.setPassword(password);
                beans.add(bean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    /**
     * 判断当前账户是否存在
     * @param name
     * @return
     */
    public boolean isExists(String name) {
        User user = get(name);
        return user != null;
    }

    /**
     * 获取名称
     * @param name
     * @return
     */
    public User get(String name) {
        User bean = null;
        try(
                Connection c = DBUtil.getConnection();
                Statement s = c.createStatement();
        ) {
            String sql = "select * from User where name =" + name;

            ResultSet rs = s.executeQuery(sql);

            if(rs.next()) {
                bean = new User();
                int id = rs.getInt(1);
                String password = rs.getString(3);
                bean.setId(id);
                bean.setName(name);
                bean.setPassword(password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public User get(String name, String password) {
        User bean = null;
        String sql = "select * from User where name = ? and password = ?";

        try(
                Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql);
        ) {

            ps.setString(1, name);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                bean = new User();
                int id = rs.getInt(1);
                bean.setId(id);
                bean.setName(name);
                bean.setPassword(password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

}
