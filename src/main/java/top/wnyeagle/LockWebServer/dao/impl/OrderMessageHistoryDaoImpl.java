package top.wnyeagle.LockWebServer.dao.impl;

import top.wnyeagle.LockWebServer.dao.BaseDao;
import top.wnyeagle.LockWebServer.dao.OrderMessageHistoryDao;
import top.wnyeagle.LockWebServer.pojo.OrderMessage;
import top.wnyeagle.LockWebServer.pojo.OrderMessageHistory;
import top.wnyeagle.LockWebServer.util.DBUtil;

import java.sql.Connection;
import java.sql.Timestamp;

/**
 * @Description
 * @ClassName OrderMessageHistoryDaoImpl
 * @Author wny
 * @Date 2021/3/11 10:35
 * @Version 1.0
 */

public class OrderMessageHistoryDaoImpl extends BaseDao<OrderMessageHistory> implements OrderMessageHistoryDao {
    @Override
    public OrderMessageHistory getMsgHistory(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from OrderMessageHistory where lockid = ?";
        OrderMessageHistory messageHistory = getSingleObject(con, sql, lockId);
        return messageHistory;
    }

    //根据对象插入记录
    @Override
    public int insertByOrderMessage(OrderMessage orderMessage) throws Exception{
        Connection con = DBUtil.getConnection();
        String sql = "insert into orderMessageHistory values(?,?,?)";
        return update(con,sql,orderMessage.getUserId(),orderMessage.getLockId(),orderMessage.getOrderTime());
    }

    @Override
    public Timestamp getOrderTimeBylockId(Integer lockId) throws Exception{
        Connection con = DBUtil.getConnection();
        String sql = "select orderTime from orderMessageHistory where lockId = ?";
        Timestamp value = (Timestamp) getValue(con, sql, lockId);
        return value;
    }

    @Override
    public Boolean deleteByLockId(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from orderMessageHistory where lockId = ?";
        int update = update(con, sql, lockId);
        if (update>0)return true;
        return false;
    }
}
