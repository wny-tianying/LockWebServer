package top.wnyeagle.LockWebServer.service;

import top.wnyeagle.LockWebServer.pojo.SystemHistory;
import top.wnyeagle.LockWebServer.pojo.User;
import top.wnyeagle.LockWebServer.util.dataTemplete.ManagerLockMsg;

import java.util.List;

/**
 * @Description 关于用户方面的业务
 * @InterfaceName UserService
 * @Author wny
 * @Date 2021/3/6 10:50
 * @Version 1.0
 */

public interface UserService {
    public User nameOrPasswordIsCorrect(String email, String Password) throws Exception;

    //用户注册的方法
    public String userSignUp(User user) throws Exception;

    //用户更新自己的信息
    Boolean updateUser(User updateUser) throws Exception;

    //
    User getUserByEmail(String userEmail) throws Exception;

    //获取所有除了本人的用户信息
    List<User> getUsersExcludeSelf(int userId) throws Exception;

    //删除对应的用户
    Boolean delUsersById(Integer[] user_ids) throws Exception;

    //删除对应的锁
    Boolean delLocksByIds(Integer[] lockIds) throws  Exception;

    List<SystemHistory> getSystemHistory() throws Exception;

    User getUserById(Integer userId) throws Exception;
    //管理员的管理界面的锁管理界面获取的锁信息模板
    ManagerLockMsg getManagerLockMsg(Integer lockId) throws Exception;

    Boolean modifyLockAbleToUse(Integer lockId, Boolean ableToUse) throws Exception;

    SystemHistory getSystemHistoryByTableId(Integer tableId) throws Exception;

    Boolean delSystemHistory(Integer tableId) throws Exception;

    Boolean delSystemManyHistory(Integer[] tableIds) throws Exception;
}
