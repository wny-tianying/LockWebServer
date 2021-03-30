package top.wnyeagle.LockWebServer.dao.impl;

import top.wnyeagle.LockWebServer.dao.BaseDao;
import top.wnyeagle.LockWebServer.dao.UserDao;
import top.wnyeagle.LockWebServer.pojo.User;
import top.wnyeagle.LockWebServer.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description
 * @ClassName UserDaoImpl
 * @Author wny
 * @Date 2021/3/5 13:14
 * @Version 1.0
 */

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    @Override
    public List<User> getUserByEmail(String email) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from user where email = ?";
        List<User> users = getForList(con, sql, email);
        return users;
    }

    @Override
    public User queryBySchoolNumber(Integer schoolNumber) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from user where schoolNumber = ?";
        return getSingleObject(con,sql,schoolNumber);
    }

    @Override
    public User queryByEmail(String email) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from user where email = ?";
        return getSingleObject(con,sql,email);
    }

    @Override
    public List<User> getAllUsersExcludeSelf(int userId) throws Exception {
        Connection connection = DBUtil.getConnection();
        String sql = "select * from user where userId != ?";
        return getForList(connection,sql,userId);
    }

    @Override
    public User getUserById(Integer userId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from user where userId = ?";
        return getSingleObject(con,sql,userId);
    }

    @Override
    public int insert(User user) throws SQLException {
        Connection con = DBUtil.getConnection();
        String sql  = "insert into user(userName,password,age,schoolNumber,authority,email)values(?,?,?,?,?,?)";
        int resultCount = update(con,sql,user.getUserName(),user.getPassword(),user.getAge(),user.getSchoolNumber(),user.getAuthority(),user.getEmail());
        return resultCount;
    }

    @Override
    public int update(User user) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "update user set userName = ?, password = ?, age = ?, schoolNumber = ?," +
                " authority = ?, email = ? where userId = ?";
        int effectCount = update(con,sql,user.getUserName(),user.getPassword(),user.getAge(),user.getSchoolNumber(),
                user.getAuthority(),user.getEmail(),user.getUserId());
        return effectCount;
    }

    @Override
    public int delete(User user) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from user where userId = ?";
        int effectCount = update(con,sql,user.getUserId());
        return effectCount;
    }

    @Override
    public int deleteById(Integer id) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from user where userId = ?";
        int effectCount = update(con,sql,id);
        return effectCount;
    }

    @Override
    public List<User> getAllObject() throws Exception{
        Connection con = DBUtil.getConnection();
        String sql = "select * from user";
        List<User> users = getForList(con, sql);
        return users;
    }
}
