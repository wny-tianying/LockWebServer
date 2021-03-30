package top.wnyeagle.LockWebServer.controller;

import top.wnyeagle.LockWebServer.pojo.User;
import top.wnyeagle.LockWebServer.service.UserService;
import top.wnyeagle.LockWebServer.service.impl.UserServiceImpl;
import top.wnyeagle.LockWebServer.util.DataUtil;
import top.wnyeagle.LockWebServer.util.dataTemplete.ReturnDataTemplete;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description
 * @ClassName UserServlet
 * @Author wny
 * @Date 2021/3/6 10:52
 * @Version 1.0
 */
@WebServlet(value = "/BasicUserOperate")
public class UserServlet extends BaseServlet {
    //服务层的应用
    private UserService userService = new UserServiceImpl();

    public void loginAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user;
        ReturnDataTemplete returnDataTemplete;
        try {
            user = userService.nameOrPasswordIsCorrect(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (user != null) {
            //如果用户存在，且密码正确，则将姓名和权限，都存储在Session当中
            HttpSession session = req.getSession();
            session.setAttribute("userEmail", email);
            session.setAttribute("userName", user.getUserName());
            session.setAttribute("authority", user.getAuthority());
            if (user.getAuthority().equals(User.USER)) {
                returnDataTemplete = new ReturnDataTemplete(false, true, "pages/phone/loginAfter/phone_manager.html", null);
            }else {
                returnDataTemplete =new ReturnDataTemplete(false, true, "pages/phone/loginAfter/administor_manager.html", null);
            }
        } else {
            returnDataTemplete = new ReturnDataTemplete(true, null, null, null);
        }

        resp.getWriter().write(DataUtil.getObjectToString(returnDataTemplete));
    }

    /**
     * 用户注册的方法
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    public void signUp(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(null, req.getParameter("userName"), req.getParameter("password"),
                Integer.valueOf(req.getParameter("age")), Integer.valueOf(req.getParameter("schoolNumber")),
                User.USER, req.getParameter("email"));
        resp.getWriter().write(userService.userSignUp(user));
    }
}
