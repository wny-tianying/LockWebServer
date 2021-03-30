package top.wnyeagle.LockWebServer.dao;

import top.wnyeagle.LockWebServer.pojo.OrderMessage;

import java.util.List;

/**
 * @Description
 * @InterfaceName OrderMessageDao
 * @Author wny
 * @Date 2021/3/5 17:11
 * @Version 1.0
 */

public interface OrderMessageDao {
    //根据用户的id获取预约信息
    public List<OrderMessage> getOrdersByUserId(Integer id) throws Exception;

    //根据锁的id获取预约信息
    public List<OrderMessage> getOrdersByLockId(Integer lockId) throws Exception;

    //根据锁的id和用户的id增加表的数据
    Integer insertById(Integer lockId, Integer userId) throws Exception;

    //根据用户和锁的id删除预约的锁
    int deleteByUserAndLockId(Integer userId, Integer lockId) throws Exception;

    //判断当前的预约表中是否有用户id为userId且锁的id为lockId的记录
    Boolean isUserAndLockId(Integer userId, Integer lockId) throws Exception;

    //根据用户id和锁id获取orderMessage
    OrderMessage getOrdersByUserIdAndLockId(Integer userId, Integer lockId) throws Exception;

    //根据对应的用户id删除
    Integer delByUserId(Integer userId) throws Exception;
}
