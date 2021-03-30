package top.wnyeagle.LockWebServer.dao;

import top.wnyeagle.LockWebServer.pojo.UserToLock;

import java.util.List;

/**
 * @Description 正在使用的锁的相关数据的操作，对象是UserToLock表
 * @InterfaceName UserMessageDao
 * @Author wny
 * @Date 2021/3/5 17:11
 * @Version 1.0
 */

public interface UserToLockDao {
    /**
     * 根据用户的id获取用户正在使用的所有锁的信息，查询UserToLock表
     * @param userId
     * @return
     * @throws Exception
     */
    public List<UserToLock> getDatasByUserId(Integer userId) throws Exception;

    /**
     * 根据锁的id获取用户正在使用的所有锁的信息，查询UserToLock表
     * @param lockId 锁的id
     * @return
     * @throws Exception
     */
    public List<UserToLock> getDatasByLockId(Integer lockId) throws Exception;

    /**
     * 根据传过来的对象插入到表格当中
     * @param userToLock
     * @return
     */
    int insertByObject(UserToLock userToLock) throws Exception;

    /**
     * 判断表中是否存在用户id为userid并且锁的id为lockId的锁记录
     * @param userId
     * @param lockId
     * @return
     */
    Boolean existCurLock(Integer userId, Integer lockId) throws Exception;

    Boolean deleteByLockId(Integer lockId) throws Exception;


}
