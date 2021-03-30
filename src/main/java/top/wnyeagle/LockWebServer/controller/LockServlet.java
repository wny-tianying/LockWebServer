package top.wnyeagle.LockWebServer.controller;

import top.wnyeagle.LockWebServer.pojo.History;
import top.wnyeagle.LockWebServer.pojo.User;
import top.wnyeagle.LockWebServer.service.LockService;
import top.wnyeagle.LockWebServer.service.UserService;
import top.wnyeagle.LockWebServer.service.impl.LockServiceImpl;
import top.wnyeagle.LockWebServer.service.impl.UserServiceImpl;
import top.wnyeagle.LockWebServer.util.DBUtil;
import top.wnyeagle.LockWebServer.util.DataUtil;
import top.wnyeagle.LockWebServer.util.dataTemplete.LockSetDataTemplete;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description  所有对锁的操作都在此类当中，包括获取和操作信息
 * @ClassName LockServlet
 * @Author wny
 * @Date 2021/3/11 10:19
 * @Version 1.0
 */

@WebServlet(value = "/login/getAndOperateLockMessage")
public class LockServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private LockService lockService = new LockServiceImpl();

    /**
     * 响应主界面的设置按钮事件需要的信息，数据模板是LockSetDataTemplete
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void getCurrentLockStatus(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        获取前端传来的锁的id
        Integer lockId = Integer.valueOf(req.getParameter("lockId"));
//        获取锁的信息
        LockSetDataTemplete lockStatusMsg = lockService.getLockStatusMsg(lockId);
        String da = DataUtil.getObjectToString(lockStatusMsg);
//        封装锁的信息,并写回
        resp.getWriter().write(DataUtil.getObjectToString(lockStatusMsg));
    }

    /**
     * 获取可以预约的所有锁的id
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void getAbleToOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Integer> das = lockService.getAbleToLocks();
        String data = DataUtil.getListToString(das, Integer.class);
        resp.getWriter().write(data);
    }

    /**
     * 执行增加预约的操作,返回哪些已经被预约的锁
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void excuteIncreseOrderLock(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取用户的email
        String userEmail = (String) req.getSession().getAttribute("userEmail");
        //获取传来的数据
        String orderLockId = req.getParameter("orderLockId");
        Integer[] lockIds = DataUtil.getStringToInteger(orderLockId);
        //调用sevice
        List<Integer> integers = lockService.increseOrderMessage(lockIds, userEmail);
        if (integers.size() > 0) {
            DBUtil.getConnection().rollback();
        }
        resp.getWriter().write(DataUtil.getListToString(integers, Integer.class));
    }

    /**
     * 删除当前用户某些预约的锁
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    public void deleteHasOrder(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取当前用户的email
        String userEmail = (String) req.getSession().getAttribute("userEmail");
        //获取前端选中的需要删除的锁的id
        String orderLockId = req.getParameter("deleteLockIds");
        Integer[] lockIds = DataUtil.getStringToInteger(orderLockId);
        //调用Service
        Boolean isSuccess = lockService.deleteOrderMessage(userEmail, lockIds);
        resp.getWriter().write(DataUtil.getObjectToString(isSuccess));
    }


    /**
     * 将当前预约的锁打开，进行使用
     * @param req
     * @param resp
     * @throws Exception
     */
    public void startToUse(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取当前用户的email
        String userEmail = (String) req.getSession().getAttribute("userEmail");

        Integer lockId = Integer.valueOf(req.getParameter("lockId"));
        //如果使用正常就返回true
        Boolean useRst =lockService.startToUse(userEmail,lockId);

        resp.getWriter().write(DataUtil.getObjectToString(useRst));
    }


    /**
     * 结束当前的锁的使用，实现锁的开启
     * @param req
     * @param resp
     * @throws Exception
     */
    public void endToUse(HttpServletRequest req,HttpServletResponse resp) throws  Exception{
        //获取当前用户的email
        String userEmail = (String) req.getSession().getAttribute("userEmail");
        Integer lockId = Integer.valueOf(req.getParameter("lockId"));
        //正常的使用返回true
        Boolean useRst =lockService.endToUse(userEmail,lockId);
        resp.getWriter().write(DataUtil.getObjectToString(useRst));
    }

    /**
     * 获取当前用户的使用历史
     * @param req
     * @param resp
     * @throws Exception
     */
    public void getUserLockHistory(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        String userEmail =(String) req.getSession().getAttribute("userEmail");
        User userByEmail = userService.getUserByEmail(userEmail);
        List<History> histories = lockService.getLockHistoryByUseId(userByEmail.getUserId());
        resp.getWriter().write(DataUtil.getListToString(histories,History.class));
    }

    /**
     * 有选择性地删除当前用户的历史记录
     * @param req
     * @param resp
     * @throws Exception
     */
    public void deleteHistory(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        Integer userId = userService.getUserByEmail((String) req.getSession().getAttribute("userEmail")).getUserId();
        Integer []locks = DataUtil.getStringToInteger(req.getParameter("locks")) ;
        Boolean rst = lockService.deleteHistoryByLockAndUserId(userId,locks);
        resp.getWriter().write(rst.toString());
    }

    public void deleteAll(HttpServletRequest req,HttpServletResponse resp) throws  Exception{
        Integer userId = userService.getUserByEmail((String) req.getSession().getAttribute("userEmail")).getUserId();
        Boolean rst = lockService.deleteHistoryByUserId(userId);
        resp.getWriter().write(rst.toString());
    }
}
