package top.wnyeagle.LockWebServer.service;

import top.wnyeagle.LockWebServer.pojo.History;
import top.wnyeagle.LockWebServer.pojo.Lock;
import top.wnyeagle.LockWebServer.util.dataTemplete.LockSetDataTemplete;
import top.wnyeagle.LockWebServer.util.dataTemplete.LockUsingMessage;

import java.util.List;

/**
 * @Description 有关的锁信息的获取
 * @InterfaceName LockService
 * @Author wny
 * @Date 2021/3/9 17:22
 * @Version 1.0
 */

public interface LockService {
    //获取当前用户的锁的情况，包括：已经预约了的锁，已经在使用的锁
    public List<LockUsingMessage> getCurrentLockStatus(String email) throws Exception;

    /*获取锁id为lockID的锁的状态信息*/
    public LockSetDataTemplete getLockStatusMsg(Integer lockId) throws Exception;

    /**
     * 获取所有可以预约的锁的id
     */
    public List<Integer> getAbleToLocks() throws  Exception;

    /**
     * 增加预约的锁
     * @param lockIds
     * @return
     * @throws Exception
     */
    public List<Integer> increseOrderMessage(Integer[] lockIds,String email) throws Exception;

    Boolean deleteOrderMessage(String userEmail, Integer[] lockIds)throws Exception;

    /**
     * 调用该方法实现锁的正式使用
     * @param userEmail
     * @param lockId
     * @return
     */
    Boolean startToUse(String userEmail, Integer lockId) throws Exception;

    Boolean endToUse(String userEmail, Integer lockId) throws Exception;

    /**
     * 通过用户的id获取锁的使用记录
     * @param userId
     * @return
     */
    List<History> getLockHistoryByUseId(Integer userId) throws Exception;
    //通过用户和锁地id删除对应地记录
    Boolean deleteHistoryByLockAndUserId(Integer userId,Integer[] lockId) throws Exception;

    //删除用户id为userId的所有的历史记录
    Boolean deleteHistoryByUserId(Integer userId) throws Exception ;

    //获取所有的锁
    List<Lock> getLocks() throws Exception;
}
