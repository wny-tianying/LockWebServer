package top.wnyeagle.LockWebServer.dao;

import top.wnyeagle.LockWebServer.pojo.Lock;

import java.util.List;

/**
 * @Description
 * @InterfaceName LockDao
 * @Author wny
 * @Date 2021/3/5 17:10
 * @Version 1.0
 */

public interface LockDao {
    //获取所有可以用的锁
    List<Lock> getNotUsingLock() throws Exception;

    //当前的锁号是否已被占用
    Boolean isUse(Integer lockId) throws Exception;

    //设置id为lockID的锁为已被占用状态
    Integer setLockUsingIsNot(Integer lockId) throws Exception;

    //设置id为lockId的锁为未被占用的状态
    int setLockUsingIsTrue(Integer lockId) throws Exception;

    //获取锁（不包括正在使用的锁，但是包括已经预约的锁）
    List<Lock> getLocks() throws Exception;

    //删除锁
    Boolean delLocks(Integer lockId) throws Exception;

    Lock getLockById(Integer lockId) throws Exception;

    Boolean updateByObject(Lock lockById) throws Exception;
}
