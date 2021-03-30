package top.wnyeagle.LockWebServer.dao;

import top.wnyeagle.LockWebServer.pojo.OrderMessage;
import top.wnyeagle.LockWebServer.pojo.OrderMessageHistory;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Description
 * @ClassName OrderMessageHistoryDao
 * @Author wny
 * @Date 2021/3/11 10:34
 * @Version 1.0
 */

public interface OrderMessageHistoryDao {
    //根据锁id获取预约历史记录的信息
    OrderMessageHistory getMsgHistory(Integer lockId) throws Exception;

    //根据对象插入记录
    int insertByOrderMessage(OrderMessage orderMessage) throws Exception;

    //根据锁的id获取对应的预约时间
    Timestamp getOrderTimeBylockId(Integer lockId) throws Exception;

    Boolean deleteByLockId(Integer lockId) throws Exception;
}
