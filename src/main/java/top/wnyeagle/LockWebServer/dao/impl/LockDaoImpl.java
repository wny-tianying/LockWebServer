package top.wnyeagle.LockWebServer.dao.impl;

import top.wnyeagle.LockWebServer.dao.BaseDao;
import top.wnyeagle.LockWebServer.dao.LockDao;
import top.wnyeagle.LockWebServer.pojo.Lock;
import top.wnyeagle.LockWebServer.util.DBUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @Description
 * @ClassName LockDaoImpl
 * @Author wny
 * @Date 2021/3/6 9:56
 * @Version 1.0
 */

public class LockDaoImpl extends BaseDao<Lock> implements LockDao {

    /**
     * 获取所有可以预约的锁
     * @return
     * @throws Exception
     */
    @Override
    public List<Lock> getNotUsingLock() throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from `lock` where lockUsingStatus = false and ableToUse = true";
        return getForList(con,sql);
    }

    //判断是否被占用
    @Override
    public Boolean isUse(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select lockUsingStatus from `lock` where lockId = ?";
        return getValue(con,sql,lockId);
    }

    //将其占用
    @Override
    public Integer setLockUsingIsNot(Integer lockId) throws Exception{
        Connection con = DBUtil.getConnection();
        String sql = "update `lock` set lockUsingStatus = true where lockId = ?";
        return update(con,sql,lockId);
    }

    /**
     * 设置id为lockId的锁为未被占用的状态
     * @param lockId
     * @return
     * @throws Exception
     */
    @Override
    public int setLockUsingIsTrue(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "update `lock` set lockUsingStatus = false where lockId = ?";
        return update(con,sql,lockId);
    }

    @Override
    public List<Lock> getLocks() throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from `lock` where lockId not in (select lockId from userToLock)";
        return getForList(con,sql);
    }

    @Override
    public Boolean delLocks(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from `lock` where lockId = ?";
        int line = update(con,sql,lockId);
        if (line>0)return true;
        return false;
    }

    @Override
    public Lock getLockById(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from `lock` where lockId = ?";
        return getSingleObject(con,sql,lockId);
    }

    @Override
    public Boolean updateByObject(Lock lockById) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "update `lock` set lockUsingStatus = ?,lockWorkingStatus =?,ableToUse = ? where lockId = ?";
        int eff = update(con,sql,lockById.getLockUsingStatus(),lockById.getLockWorkingStatus(),lockById.getAbleToUse(),lockById.getLockId());
        if (eff>0)return true;
        return false;
    }
}
