package top.wnyeagle.LockWebServer.dao.impl;

import top.wnyeagle.LockWebServer.dao.BaseDao;
import top.wnyeagle.LockWebServer.dao.HistoryDao;
import top.wnyeagle.LockWebServer.pojo.History;
import top.wnyeagle.LockWebServer.util.DBUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @Description
 * @ClassName HistoryDaoImpl
 * @Author wny
 * @Date 2021/3/6 9:57
 * @Version 1.0
 */

public class HistoryDaoImpl extends BaseDao<History> implements HistoryDao{
    @Override
    public Boolean insertByObject(History history) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "insert into history(userId,lockId,useStartTime,useEndTime,orderTime) values(?,?,?,?,?)";
        int rst = update(con,sql,history.getUserId(),history.getLockId(),history.getUseStartTime(),history.getUseEndTime(),history.getOrderTime());
        if (rst>0)return true;
        return false;
    }

    @Override
    public List<History> getHistorysByUserId(Integer userId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from history where userId = ?";
        return getForList(con,sql,userId);
    }

    @Override
    public int deleteByLockAndUserId(Integer userId, Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from history where userId = ? and lockId = ?";
        return update(con,sql,userId,lockId);
    }

    @Override
    public int deleteByUserId(Integer userId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from history where userId = ?";
        return update(con,sql,userId);
    }

    @Override
    public Boolean delByLockId(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from history where lockId = ?";
        int update = update(con, sql, lockId);
        if (update>0)return true;
        return false;
    }

}
