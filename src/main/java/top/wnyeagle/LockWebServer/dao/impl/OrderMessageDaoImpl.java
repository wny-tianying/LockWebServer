package top.wnyeagle.LockWebServer.dao.impl;

import top.wnyeagle.LockWebServer.dao.BaseDao;
import top.wnyeagle.LockWebServer.dao.OrderMessageDao;
import top.wnyeagle.LockWebServer.pojo.OrderMessage;
import top.wnyeagle.LockWebServer.util.DBUtil;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Description
 * @ClassName OrderMessageDaoImpl
 * @Author wny
 * @Date 2021/3/6 10:01
 * @Version 1.0
 */

public class OrderMessageDaoImpl extends BaseDao<OrderMessage> implements OrderMessageDao {
    @Override
    public List<OrderMessage> getOrdersByUserId(Integer id) throws Exception{
        Connection con  = DBUtil.getConnection();
        String sql = "select * from OrderMessage where userId = ?";
        List<OrderMessage> data = getForList(con, sql, id);
        return data;
    }

    @Override
    public List<OrderMessage> getOrdersByLockId(Integer lockId) throws Exception {
        Connection con  = DBUtil.getConnection();
        String sql = "select * from OrderMessage where lockId = ?";
        List<OrderMessage> data = getForList(con, sql, lockId);
        return data;
    }

    /**
     * 根据锁的id和用户的id增加表的数据
     * @param lockId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertById(Integer lockId, Integer userId) throws Exception {
        //获取一下当前的时间
        Timestamp curTime = new Timestamp(System.currentTimeMillis());
        Connection  con = DBUtil.getConnection();
        String sql = "insert into orderMessage values(?,?,?)";
        return update(con,sql,userId,lockId,curTime);
    }

    /**
     * 根据用户和锁的id删除预约的锁
     * @param userId
     * @param lockId
     * @return
     * @throws Exception
     */
    @Override
    public int deleteByUserAndLockId(Integer userId, Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from orderMessage where userId = ? and lockId = ?";
        return update(con,sql,userId,lockId);
    }

    /**
     * 判断当前的预约表中是否有用户id为userId且锁的id为lockId的记录
     * @param userId
     * @param lockId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean isUserAndLockId(Integer userId, Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from orderMessage where userId = ? and lockId = ?";
        List<OrderMessage> messages = getForList(con, sql, userId, lockId);
        if (messages.size()>0)return true;
        return false;
    }

    /**
     * 根据用户id和锁id获取orderMessage
     * @param userId
     * @param lockId
     * @return
     * @throws Exception
     */
    @Override
    public OrderMessage getOrdersByUserIdAndLockId(Integer userId, Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from orderMessage where userId = ? and lockId = ?";
        List<OrderMessage> messages = getForList(con, sql, userId, lockId);
        if (messages.size()>0)
        return messages.get(0);
        return null;
    }

    @Override
    public Integer delByUserId(Integer userId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from orderMessage where userId = ?";
        return update(con,sql,userId);
    }


}
