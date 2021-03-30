package top.wnyeagle.LockWebServer.service.impl;

import top.wnyeagle.LockWebServer.dao.*;
import top.wnyeagle.LockWebServer.dao.impl.*;
import top.wnyeagle.LockWebServer.pojo.*;
import top.wnyeagle.LockWebServer.util.DBUtil;
import top.wnyeagle.LockWebServer.util.DataUtil;
import top.wnyeagle.LockWebServer.util.LockDataUtil;
import top.wnyeagle.LockWebServer.util.dataTemplete.LockSetDataTemplete;
import top.wnyeagle.LockWebServer.util.dataTemplete.LockUsingMessage;
import top.wnyeagle.LockWebServer.service.LockService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName LockServiceImpl
 * @Author wny
 * @Date 2021/3/9 17:32
 * @Version 1.0
 */

public class LockServiceImpl implements LockService {
    private UserDao userDao;
    private OrderMessageDao orderMessageDao;
    private UserToLockDao lockDao;
    private OrderMessageHistoryDao orderMessageHistoryDao;
    private LockDao lock;
    private HistoryDao historyDao;
    private SystemHistoryDao systemHistoryDao;

    {
        userDao= new UserDaoImpl();
        orderMessageDao = new OrderMessageDaoImpl();
        lockDao = new UserToLockDaoImpl();
        orderMessageHistoryDao = new OrderMessageHistoryDaoImpl();
        lock = new LockDaoImpl();
        historyDao = new HistoryDaoImpl();
        systemHistoryDao = new SystemHistoryDaoImpl();
    }


    /**
     * 通过用户的email获取当前用户的已经预约的锁和正在使用的锁，如若没有则返回大小为0的集合
     * @param email
     * @return
     * @throws Exception
     */
    @Override
    public List<LockUsingMessage> getCurrentLockStatus(String email) throws Exception{

        List<LockUsingMessage> data = new ArrayList<>();
        //第一步获取用户的id(可以间接的获取user的所有信息)
        User user = userDao.queryByEmail(email);
        if (user!=null){
            //存在此人，不为空才能继续操作
            //第二步获取该用户预约的锁的情况，查询OrderMessage表
            List<OrderMessage> orderData = orderMessageDao.getOrdersByUserId(user.getUserId());
            //第三步获取该用户正在使用的锁的情况，查询UserToLock表
            List<UserToLock> datasByUserId = lockDao.getDatasByUserId(user.getUserId());
            //第四步将数据封装在集合当中
            for (OrderMessage tem:orderData){
                data.add(new LockUsingMessage(tem.getLockId(),LockUsingMessage.HAS_ORDERED));
            }
            for (UserToLock tem:datasByUserId){
                data.add(new LockUsingMessage(tem.getLockId(),LockUsingMessage.IS_USING));
            }
        }
        return data;
    }

    /**
     * 获取锁的状态信息，包括锁的预约时间，使用时间，使用状态
     * @param lockId
     * @return
     * @throws Exception
     */
    @Override
    public LockSetDataTemplete getLockStatusMsg(Integer lockId) throws Exception {
        LockSetDataTemplete statusData = null;
        //首先判断是否是预约的状态
        List<OrderMessage> orders = orderMessageDao.getOrdersByLockId(lockId);
        if (orders.size()>0){
            //是预约的状态
            statusData = new LockSetDataTemplete(lockId,orders.get(0).getOrderTime(),null,LockUsingMessage.HAS_ORDERED);
        }else {
            //非预约的状态
            List<UserToLock> datas = lockDao.getDatasByLockId(lockId);
            if(datas.size()>0){
                //正在使用的状态
                OrderMessageHistory msgHistory = orderMessageHistoryDao.getMsgHistory(lockId);
                statusData = new LockSetDataTemplete(lockId,msgHistory.getOrderTime(),datas.get(0).getUseTime(),LockUsingMessage.IS_USING);
            }else{
                //也不在使用的状态,一般不存在此种情况
            }
        }
        return statusData;
    }

    /**
     * 获取所有可以预约的锁的id
     * @return
     * @throws Exception
     */
    public List<Integer> getAbleToLocks() throws  Exception{
        List<Lock> usingLocks = lock.getNotUsingLock();
        List<Integer> retuData = new ArrayList<>();
        for (Lock tem:usingLocks){
            retuData.add(tem.getLockId());
        }
        return retuData;
    }

    /**
     * 添加预约的信息
     * @param lockIds
     * @return 返回的是哪些锁不满足条件
     * @throws Exception
     */
    @Override
    public List<Integer> increseOrderMessage(Integer[] lockIds,String email) throws Exception {
        //获取用户的id
        List<User> userByEmail = userDao.getUserByEmail(email);
        List<Integer> setRst = new ArrayList<>();
        for (Integer w:lockIds) {
            //第一步在数据库中查询，当前的id的锁是否被使用
            Boolean rst=lock.isUse(w);
            if (!rst){
                //可以用，开始预约
                //第一步，设置orderMessage表中的lockUsingStatus值为0
                Integer effectLines =  lock.setLockUsingIsNot(w);
                //第二步，将增加锁的信息增加到orderMessage表当中
                Integer lines = orderMessageDao.insertById(w,userByEmail.get(0).getUserId());
            }
            else{
                //已被占用的情况就需要回滚并返回结果
                setRst.add(w);
            }
        }
        return setRst;
    }


    /**
     * 删除预约表中当前用户预约的锁
     * @param userEmail 用户的email
     * @param lockIds 要删除的锁的ids
     * @return
     */
    @Override
    public Boolean deleteOrderMessage(String userEmail, Integer[] lockIds) throws Exception{
        //获取用户的id
        List<User> userByEmail = userDao.getUserByEmail(userEmail);
        //标志处理过程是否出错
        Boolean happenError = false;
        //删除对应的数据
        for (Integer tem:lockIds){
            int effectLines =  orderMessageDao.deleteByUserAndLockId(userByEmail.get(0).getUserId(),tem);
            if (effectLines<0){
                happenError=true;break;
            }
            //设置当前锁号的锁是可以使用的
            int rst = lock.setLockUsingIsTrue(tem);
            if (rst<0){
                happenError=true;break;
            }
        }
        if (happenError){
            DBUtil.getConnection().rollback();
            return false;
        }
        return true;
    }

    /**
     * 调用该方法实现锁的正式使用
     * @param userEmail
     * @param lockId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean startToUse(String userEmail, Integer lockId) throws Exception {
        Boolean rst = false;
        //获取用户的id
        List<User> userByEmail = userDao.getUserByEmail(userEmail);
        //判断当前的的锁是否为当前用户预约的
        Boolean isMatched = orderMessageDao.isUserAndLockId(userByEmail.get(0).getUserId(),lockId);
        if (isMatched){
            /*//发送信息到储物柜开锁等操作
                //将当前的锁的id存储到needToOpenStart集合当中
            LockDataUtil.addStartLocks(lockId);
                //循环判断hasClosedStart集合当中是否有当前的id，也就是判断是否正常地关闭
            while (LockDataUtil.isExitHasClosedStart(lockId)){}*/
            //将orderMessage中当前的记录存储到OrderMessageHistory表当中，主要是获取时间
            OrderMessage orderMessage = orderMessageDao.getOrdersByUserIdAndLockId(userByEmail.get(0).getUserId(),lockId);
            //将orderMessage中的记录删除
            orderMessageDao.deleteByUserAndLockId(userByEmail.get(0).getUserId(),lockId);
            //将上面获取的记录存储到orderMessageHistory
            int count =orderMessageHistoryDao.insertByOrderMessage(orderMessage);

            if(count>0){
                //向userToLock表中插入数据
                UserToLock userToLock = new UserToLock(userByEmail.get(0).getUserId(),lockId,new Timestamp(System.currentTimeMillis()));
                int effLines = lockDao.insertByObject(userToLock);
                if (effLines>0){
                    rst = true;
                }else {//向userToLock表中插入数据失败
                    DBUtil.getConnection().rollback();
                }
            }else {//存储到orderMessageHistory失败
                DBUtil.getConnection().rollback();
            }
        }
        return rst;
    }

    @Override
    public Boolean endToUse(String userEmail, Integer lockId) throws Exception {
        Boolean rst = false;
        //获取用户的id
        List<User> userByEmail = userDao.getUserByEmail(userEmail);
        Integer userId = userByEmail.get(0).getUserId();
        //判断当前的锁是否为当前用户使用的锁
        Boolean use = lockDao.existCurLock(userId,lockId);
        if (use){
            /*//发送信息到储物柜开锁等操作
                //将当前的锁的id存储到needToOpenStart集合当中
            LockDataUtil.addStartLocks(lockId);
                //循环判断hasClosedStart集合当中是否有当前的id，也就是判断是否正常地关闭
            while (LockDataUtil.isExitHasClosedStart(lockId)){}*/

            //将数据插入到history的表格当中
                //从orderMessageHistory当中读取预约的时间
            Timestamp orderTime = orderMessageHistoryDao.getOrderTimeBylockId(lockId);
                //将当前的锁id对应的数据行删除
            Boolean deleteRst = orderMessageHistoryDao.deleteByLockId(lockId);
            if (deleteRst){
                //获取使用时间并且将userToLock表中的数据删除
                List<UserToLock> datasByLockId = lockDao.getDatasByLockId(lockId);
                Timestamp useTime = datasByLockId.get(0).getUseTime();
                Boolean del_UTD_RSt =  lockDao.deleteByLockId(lockId);
                if (del_UTD_RSt){
                    //向history表当中插入当前的数据
                    Boolean insertRst= historyDao.insertByObject(new History(userId,lockId,orderTime,useTime,new Timestamp(System.currentTimeMillis())));
                    systemHistoryDao.insertByHistory(new History(userId,lockId,orderTime,useTime,new Timestamp(System.currentTimeMillis())));
                    //将lock表中的记录修改为为被占用状态
                    Integer integer = lock.setLockUsingIsTrue(lockId);
                    if (insertRst&&integer>0) rst = true;
                }
            }


        }
        if (!rst) DBUtil.getConnection().rollback();
        return rst;
    }

    @Override
    public List<History> getLockHistoryByUseId(Integer userId) throws Exception{
        List<History> data = historyDao.getHistorysByUserId(userId);
        return data;
    }

    @Override
    public Boolean deleteHistoryByLockAndUserId(Integer userId,Integer[] lockId) throws Exception {
        Boolean rst = true;
        for (int i =0;i<lockId.length;i++){
            int effectLines = historyDao.deleteByLockAndUserId(userId,lockId[i]);
            if (effectLines<0) rst = false;
        }
        if (!rst) DBUtil.getConnection().rollback();
        return rst;
    }

    @Override
    public Boolean deleteHistoryByUserId(Integer userId) throws Exception {
        int effectLines = historyDao.deleteByUserId(userId);
        if (effectLines>0)return  true;
        return false;
    }

    /**
     *获取所有的锁（不包括正在使用的锁，但是包括已经预约的锁）
     * @return
     * @throws Exception
     */
    @Override
    public List<Lock> getLocks() throws Exception {
        List<Lock> locks = lock.getLocks();
        return locks;
    }


}
