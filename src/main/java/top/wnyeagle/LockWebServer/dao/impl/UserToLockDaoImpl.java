package top.wnyeagle.LockWebServer.dao.impl;

import top.wnyeagle.LockWebServer.dao.BaseDao;
import top.wnyeagle.LockWebServer.dao.UserToLockDao;
import top.wnyeagle.LockWebServer.pojo.UserToLock;
import top.wnyeagle.LockWebServer.util.DBUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @Description
 * @ClassName UserToLockDaoImpl
 * @Author wny
 * @Date 2021/3/6 9:59
 * @Version 1.0
 */

public class UserToLockDaoImpl extends BaseDao<UserToLock> implements UserToLockDao {

    /**
     * 根据用户的id获取用户正在使用的所有锁的信息，查询UserToLock表
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<UserToLock> getDatasByUserId(Integer userId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from userToLock where userId = ? ";
        List<UserToLock> data = getForList(con, sql, userId);
        return data;
    }

    /**
     * 根据锁的id获取用户正在使用的所有锁的信息，查询UserToLock表
     * @param lockId 锁的id
     * @return
     * @throws Exception
     */
    @Override
    public List<UserToLock> getDatasByLockId(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "select * from userToLock where lockId = ? ";
        List<UserToLock> data = getForList(con, sql, lockId);
        return data;
    }

    /**
     * 根据传过来的对象插入到表格当中
     * @param userToLock
     * @return
     * @throws Exception
     */
    @Override
    public int insertByObject(UserToLock userToLock) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "insert into userToLock values(?,?,?) ";
        return update(con,sql,userToLock.getUserId(),userToLock.getLockId(),userToLock.getUseTime());
    }

    /**
     * 判断表中是否存在用户id为userid并且锁的id为lockId的锁记录
     * @param userId
     * @param lockId
     * @return
     */
    @Override
    public Boolean existCurLock(Integer userId, Integer lockId) throws Exception{
        Connection con = DBUtil.getConnection();
        String sql = "select * from userToLock where userid = ? and lockId = ?";
        List<UserToLock> userToLocks = getForList(con, sql, userId, lockId);
        if (userToLocks.size()>0)return true;
        return false;
    }

    @Override
    public Boolean deleteByLockId(Integer lockId) throws Exception {
        Connection con = DBUtil.getConnection();
        String sql = "delete from userToLock where lockId = ?";
        int update = update(con, sql, lockId);
        if (update>0)return true;
        return false;
    }


}
