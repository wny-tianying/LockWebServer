package top.wnyeagle.LockWebServer.service.impl;

import top.wnyeagle.LockWebServer.dao.*;
import top.wnyeagle.LockWebServer.dao.impl.*;
import top.wnyeagle.LockWebServer.pojo.*;
import top.wnyeagle.LockWebServer.service.UserService;
import top.wnyeagle.LockWebServer.util.DBUtil;
import top.wnyeagle.LockWebServer.util.DataUtil;
import top.wnyeagle.LockWebServer.util.dataTemplete.ManagerLockMsg;
import top.wnyeagle.LockWebServer.util.dataTemplete.ReturnDataTemplete;

import java.util.List;

/**
 * @Description
 * @ClassName UserServiceImpl
 * @Author wny
 * @Date 2021/3/6 10:50
 * @Version 1.0
 */

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    private UserToLockDao userToLockDao = new UserToLockDaoImpl();
    private OrderMessageDao orderMessageDao = new OrderMessageDaoImpl();
    private HistoryDao historyDao = new HistoryDaoImpl();
    private SystemHistoryDao systemHistoryDao = new SystemHistoryDaoImpl();
    private LockDao lockDao = new LockDaoImpl();



    @Override
    public User nameOrPasswordIsCorrect(String email, String password) throws Exception {
        List<User> users = userDao.getUserByEmail(email);
        if (users.size()>0){
            //有该人
            if (users.get(0).getPassword().equals(password)){
                //有人且密码正确
                return users.get(0);
            }
            return null;
        }
        return null;
    }

    @Override
    public String userSignUp(User user) throws Exception {
        ReturnDataTemplete returnData;
        if (userDao.getUserByEmail(user.getEmail()).size()<=0||
                userDao.queryBySchoolNumber(user.getSchoolNumber())==null){
//            插入影响的行数
            int effectLine = userDao.insert(user);
            returnData = new ReturnDataTemplete(false,null,null,null);
            return DataUtil.getObjectToString(returnData);
        }else{
            returnData =  new ReturnDataTemplete(true,null,null,"用户名或邮箱名已存在");
            return DataUtil.getObjectToString(returnData);
        }
    }

    @Override
    public Boolean updateUser(User updateUser) throws Exception{
        int update = userDao.update(updateUser);
        if (update>0)return true;
        return false;
    }


    @Override
    public User getUserByEmail(String userEmail) throws Exception {
        return userDao.getUserByEmail(userEmail).get(0);
    }

    @Override
    public List<User> getUsersExcludeSelf(int userId) throws Exception{
        List<User> data = userDao.getAllUsersExcludeSelf(userId);
        return data;
    }

    @Override
    public Boolean delUsersById(Integer[] user_ids) throws Exception {
        for (Integer tem:user_ids){
            //第一步，考虑当前要删除的用户是否已经在使用某一个锁
            List<UserToLock> datasByUserId = userToLockDao.getDatasByUserId(tem);
            if (datasByUserId.size()<0){
                //第二步，删除用户、用户对应的预约、用户对应的history,SystemHistory
                int i = userDao.deleteById(tem);
                Integer integer = orderMessageDao.delByUserId(tem);
                int i1 = historyDao.deleteByUserId(tem);
                Integer integer1 = systemHistoryDao.delByUserId(tem);
                if (i<0||integer<0||i1<0||integer1<0){
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean delLocksByIds(Integer[] lockIds) throws Exception {
        Boolean rst = false;
        for (Integer tem:lockIds){
            //判断当前的锁是否使用
            List<UserToLock> datasByLockId = userToLockDao.getDatasByLockId(tem);
            if (datasByLockId.size()>0){
                return false;
            }else{
                //删除对应的锁，预约的信息，history与SystemHistory
                Boolean delLock=lockDao.delLocks(tem);
                Integer delOrder = orderMessageDao.delByUserId(tem);
                Boolean delHistory = historyDao.delByLockId(tem);
                Boolean delSysHis =  systemHistoryDao.delByLockId(tem);
                if (delLock)rst=true;
                else{
                    return false;
                }
            }
        }
        if (!rst){
            DBUtil.getConnection().rollback();
        }
        return rst;
    }

    @Override
    public List<SystemHistory> getSystemHistory() throws Exception {
        return systemHistoryDao.getAllObject();
    }

    @Override
    public User getUserById(Integer userId) throws Exception {
        User user = userDao.getUserById(userId);
        return user;
    }

    @Override
    public ManagerLockMsg getManagerLockMsg(Integer lockId) throws Exception {
        Lock lock = lockDao.getLockById(lockId);
        //获取预约当前锁的用户名
        List<OrderMessage> ordersByLockId = orderMessageDao.getOrdersByLockId(lockId);
        Boolean idOrder = false;
        String userOrderName = null;
        if (ordersByLockId.size()>0) {
            idOrder =true;
            User user = userDao.getUserById(ordersByLockId.get(0).getUserId());
            userOrderName = user.getUserName();
        }

        ManagerLockMsg managerLockMsg = new ManagerLockMsg(lockId,idOrder,userOrderName,lock.getAbleToUse());
        return managerLockMsg;
    }

    @Override
    public Boolean modifyLockAbleToUse(Integer lockId, Boolean ableToUse) throws Exception {
        Lock lockById = lockDao.getLockById(lockId);
        lockById.setAbleToUse(ableToUse);
        Boolean updateRst = lockDao.updateByObject(lockById);
        //判断修改的状态，如果改为不可用，需要将当前的锁的有关预约删除
        
        return updateRst;
    }

    /**
     * 通过系统历史的主键获取记录数据
     * @param tableId
     * @return
     * @throws Exception
     */
    @Override
    public SystemHistory getSystemHistoryByTableId(Integer tableId) throws Exception {
        return systemHistoryDao.getObjectByTableId(tableId);
    }

    @Override
    public Boolean delSystemHistory(Integer tableId) throws Exception {
        return systemHistoryDao.delByTableId(tableId);
    }

    @Override
    public Boolean delSystemManyHistory(Integer[] tableIds) throws Exception {
        for (Integer tem :tableIds){
            Boolean rst = systemHistoryDao.delByTableId(tem);
            if (!rst) return false;
        }
        return true;
    }

}
