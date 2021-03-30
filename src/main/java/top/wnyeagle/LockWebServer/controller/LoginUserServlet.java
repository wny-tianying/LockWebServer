package top.wnyeagle.LockWebServer.controller;

import top.wnyeagle.LockWebServer.dao.UserDao;
import top.wnyeagle.LockWebServer.dao.impl.UserDaoImpl;
import top.wnyeagle.LockWebServer.pojo.User;
import top.wnyeagle.LockWebServer.service.UserService;
import top.wnyeagle.LockWebServer.service.impl.UserServiceImpl;
import top.wnyeagle.LockWebServer.util.DataUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description  在用户登录之后，对有关用户信息的操作
 * @ClassName LoginUserServlet
 * @Author wny
 * @Date 2021/3/14 9:08
 * @Version 1.0
 */


@WebServlet(value = "/login/loginAfterAction")
public class LoginUserServlet extends BaseServlet{
    private UserService userService = new UserServiceImpl();

    /**
     * 用户通过访问此方法跳转到个人用户界面
     * @param req
     * @param resp
     * @throws Exception
     */
    public void skipToPersonPage(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        resp.sendRedirect("/LockWebServer/pages/phone/loginAfter/phone_person.html");
    }

    /**
     * 获取当前用户的所有信息
     * @param req
     * @param resp
     * @throws Exception
     */
    public void getUserMessage(HttpServletRequest req,HttpServletResponse resp) throws  Exception{
        //获取用户的id
        String userEmail = (String) req.getSession().getAttribute("userEmail");
        User user = userService.getUserByEmail(userEmail);
        resp.getWriter().write(DataUtil.getObjectToString(user));
    }


    public void getUserById(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        Integer userId = Integer.valueOf(req.getParameter("userId"));
        User userById = userService.getUserById(userId);
        resp.getWriter().write(DataUtil.getObjectToString(userById));
    }


    public void updateUserMessage(HttpServletRequest req,HttpServletResponse resp) throws  Exception{
        //获取用户的id
        String userEmail = (String) req.getSession().getAttribute("userEmail");
        User updateUser = userService.getUserByEmail(userEmail);
        updateUser.setUserName(req.getParameter("name"));
        updateUser.setEmail(req.getParameter("email"));
        updateUser.setAge(Integer.valueOf(req.getParameter("age")));
        Boolean rst = userService.updateUser(updateUser);
        resp.getWriter().write(rst.toString());
    }

    public void exitToLogin(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        req.getSession().invalidate();
        resp.getWriter().write("true");
    }
}
