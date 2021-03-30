package top.wnyeagle.LockWebServer.controller;

import top.wnyeagle.LockWebServer.util.dataTemplete.LockUsingMessage;
import top.wnyeagle.LockWebServer.service.LockService;
import top.wnyeagle.LockWebServer.service.impl.LockServiceImpl;
import top.wnyeagle.LockWebServer.util.DataUtil;
import top.wnyeagle.LockWebServer.util.dataTemplete.ReturnDataTemplete;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description  用于获取用户的信息，但必须是登录后
 * @ClassName ClientMessageManager
 * @Author wny
 * @Date 2021/3/8 18:00
 * @Version 1.0
 */
@WebServlet(value = "/login/getCurrentUserMessage")
public class ClientMessageManager extends BaseServlet{
    private LockService lockService = new LockServiceImpl();

    /**
     * 获取当前的登录的用户的姓名
     * @param req
     * @param resp
     */
    public void getCurrentLoginUserName(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        HttpSession session = req.getSession();
        //获取用户名
        String userName = (String) session.getAttribute("userName");
        ReturnDataTemplete dataTemplete = new ReturnDataTemplete(false,false,null,userName);

        resp.getWriter().write(DataUtil.getObjectToString(dataTemplete));
    }

    /**
     * 获取当前用户的锁的相关的使用信息，包括锁的预约信息，锁的使用状况
     * @param req
     * @param resp
     * @throws Exception
     */
    public void  getCurrentLockMessage(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("userEmail");
        List<LockUsingMessage> datas = lockService.getCurrentLockStatus(email);
        /*ReturnDataTemplete returnData =  new ReturnDataTemplete(false,false,null,DataUtil.getListToString(datas,LockUsingMessage.class));
        System.out.println(DataUtil.getObjectToString(datas));*/
        resp.getWriter().write(DataUtil.getObjectToString(datas));
    }



}
