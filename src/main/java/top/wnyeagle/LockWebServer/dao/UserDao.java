package top.wnyeagle.LockWebServer.dao;

import top.wnyeagle.LockWebServer.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description  操作用户信息的dao
 * @InterfaceName UserDao
 * @Author wny
 * @Date 2021/3/5 13:13
 * @Version 1.0
 */

public interface UserDao {
//    根据完整的用户对象实现插入
    int insert(User user) throws SQLException;
//    修改用户信息
    int update(User user) throws Exception;
//    根据对象删除
    int delete(User user) throws Exception;

    int deleteById(Integer id) throws Exception;

    /**
     * @return 查询所有对象的数据
     */
    List<User> getAllObject() throws Exception;

    //根据email查询人员
    List<User> getUserByEmail(String email) throws Exception;

    //根据学号查询
    User queryBySchoolNumber(Integer schoolNumber) throws Exception;

    /**
     * 根据email获取用户的所有信息
     * @return
     * @throws Exception
     */
    User queryByEmail(String email) throws Exception;

    /**
     * 获取所有除本人以外的用户信息
     * @param userId
     * @return
     */
    List<User> getAllUsersExcludeSelf(int userId) throws Exception;

    User getUserById(Integer userId) throws Exception;
}
