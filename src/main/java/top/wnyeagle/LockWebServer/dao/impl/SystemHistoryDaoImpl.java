package top.wnyeagle.LockWebServer.dao.impl;

import top.wnyeagle.LockWebServer.dao.BaseDao;
import top.wnyeagle.LockWebServer.dao.SystemHistoryDao;
import top.wnyeagle.LockWebServer.pojo.History;
import top.wnyeagle.LockWebServer.pojo.SystemHistory;
import top.wnyeagle.LockWebServer.util.DBUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @Description
 * @ClassName SystemHistoryDaoImpl
 * @Author wny
 * @Date 2021/3/22 17:42
 * @Version 1.0
 */

public class SystemHistoryDaoImpl extends BaseDao<SystemHistory> implements SystemHistoryDao {
    @Override
    public Integer delByUserId(Integer userId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from systemHistory where userId = ?";
        return update(con, sql, userId);
    }

    @Override
    public Boolean delByLockId(Integer tem) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from systemHistory where lockId = ?";
        int update = update(con, sql, tem);
        if (update>0)return true;
        return false;
    }

    @Override
    public List<SystemHistory> getAllObject() throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from SystemHistory";
        return getForList(con,sql);
    }

    @Override
    public void insertByHistory(History history) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "insert into systemHistory(userId,lockId,useStartTime,useEndTime,orderTime) values(?,?,?,?,?)";
        update(con,sql,history.getUserId(),history.getLockId(),history.getUseStartTime(),history.getUseEndTime(),history.getOrderTime());
    }

    @Override
    public SystemHistory getObjectByTableId(Integer tableId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from systemHistory where tableId = ?";
        return getSingleObject(con,sql,tableId);
    }

    @Override
    public Boolean delByTableId(Integer tableId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from systemHistory where tableId = ?";
        int eff = update(con,sql,tableId);
        if (eff>0)return true;
        return false;
    }
}
