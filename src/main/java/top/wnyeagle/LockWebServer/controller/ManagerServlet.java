package top.wnyeagle.LockWebServer.controller;

import top.wnyeagle.LockWebServer.pojo.Lock;
import top.wnyeagle.LockWebServer.pojo.SystemHistory;
import top.wnyeagle.LockWebServer.pojo.User;
import top.wnyeagle.LockWebServer.service.LockService;
import top.wnyeagle.LockWebServer.service.UserService;
import top.wnyeagle.LockWebServer.service.impl.LockServiceImpl;
import top.wnyeagle.LockWebServer.service.impl.UserServiceImpl;
import top.wnyeagle.LockWebServer.util.DataUtil;
import top.wnyeagle.LockWebServer.util.dataTemplete.ManagerLockMsg;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description 当前下的所有请求必须拥有管理员的权限
 * @ClassName ManagerServlet
 * @Author wny
 * @Date 2021/3/20 22:50
 * @Version 1.0
 */


@WebServlet(value = "/manager/administratorOperate")
public class ManagerServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private LockService lockService = new LockServiceImpl();

    /**
     * 获取所有用户信息
     * @param req
     * @param resp
     * @throws Exception
     */
    public void getUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        List<User> data = userService.getUsersExcludeSelf(getUserId(req));
        resp.getWriter().write(DataUtil.getListToString(data,User.class));
    }

    /**
     * 获取所有的锁相关信息
     * @param req
     * @param resp
     * @throws Exception
     */
    public void getAllLocks(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        List<Lock> locks = lockService.getLocks();
        resp.getWriter().write(DataUtil.getListToString(locks,Lock.class));
    }


    /**
     * 删除选中的users
     * @param req
     * @param resp
     * @throws Exception
     */
    public void delUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        //获取需要删除的用户id
        Integer[] user_ids = DataUtil.getStringToInteger(req.getParameter("user_ids"));
        Boolean delRst = userService.delUsersById(user_ids);
        resp.getWriter().write(DataUtil.getObjectToString(delRst));
    }

    public void delLocks(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取需要删除的锁id
        Integer[] lockIds = DataUtil.getStringToInteger(req.getParameter("data"));
        Boolean delRst = userService.delLocksByIds(lockIds);
        resp.getWriter().write(DataUtil.getObjectToString(delRst));
    }

    /**
     * 获取所有的系统历史记录
     * @param req
     * @param resp
     * @throws Exception
     */
    public void getSystemHistory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<SystemHistory> systemHistories= userService.getSystemHistory();
        resp.getWriter().write(DataUtil.getListToString(systemHistories,SystemHistory.class));
    }


    public void  getSingleUserMsg(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Integer id = Integer.valueOf(req.getParameter("data"));
        User user = userService.getUserById(id);
        resp.getWriter().write(DataUtil.getObjectToString(user));
    }

    public void modifyAuthority(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Integer userId =Integer.valueOf(req.getParameter("userId"));
        String authority = req.getParameter("authority");
        User userById = userService.getUserById(userId);
        userById.setAuthority(authority);
        Boolean aBoolean = userService.updateUser(userById);
        resp.getWriter().write(DataUtil.getObjectToString(aBoolean));
    }

    public void getManagerLockMsg(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Integer lockId = Integer.valueOf(req.getParameter("lockId"));
        ManagerLockMsg lockMsg =  userService.getManagerLockMsg(lockId);
        resp.getWriter().write(DataUtil.getObjectToString(lockMsg));
    }

    public void getSingleSystemHistory(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Integer tableId = Integer.valueOf(req.getParameter("tableId"));
        SystemHistory systemHistory= userService.getSystemHistoryByTableId(tableId);
        resp.getWriter().write(DataUtil.getObjectToString(systemHistory));
    }

    public void modifyLockAbleToUse(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Integer lockId = Integer.valueOf(req.getParameter("lockId"));
        Boolean ableToUse = Boolean.valueOf(req.getParameter("ableToUse"));
        Boolean rst =  userService.modifyLockAbleToUse(lockId,ableToUse);
        resp.getWriter().write(DataUtil.getObjectToString(rst));
    }

    public void delSystemHistory(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Integer tableId = Integer.valueOf(req.getParameter("tableId"));
        Boolean rst = userService.delSystemHistory(tableId);
        resp.getWriter().write(DataUtil.getObjectToString(rst));
    }

    public void delSystemManyHistory(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        Integer[] tableIds = DataUtil.getStringToInteger(req.getParameter("tableIds"));
        Boolean rst = userService.delSystemManyHistory(tableIds);
        resp.getWriter().write(DataUtil.getObjectToString(rst));
    }


    private int getUserId(HttpServletRequest req) throws Exception {
        String userEmail = (String) req.getSession().getAttribute("userEmail");
        return userService.getUserByEmail(userEmail).getUserId();
    }
}
