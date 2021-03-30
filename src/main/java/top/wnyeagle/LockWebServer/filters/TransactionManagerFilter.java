package top.wnyeagle.LockWebServer.filters;

import top.wnyeagle.LockWebServer.util.DBUtil;
import top.wnyeagle.LockWebServer.util.DataUtil;
import top.wnyeagle.LockWebServer.util.dataTemplete.ReturnDataTemplete;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description
 * @ClassName TransactionManager
 * @Author wny
 * @Date 2021/3/6 19:19
 * @Version 1.0
 */
@WebFilter(urlPatterns = {"/*"})
public class TransactionManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = httpServletRequest.getSession();
        chain.doFilter(req,res);
        //在下面对连接的事务进行提交和回收
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            if (con!=null){
                con.commit();
            }
        } catch (SQLException throwables) {
            //出现了异常就需要回滚
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
            ReturnDataTemplete returnDataTemplete = new ReturnDataTemplete(true,null,null,"数据更新错误");
            res.getWriter().write(DataUtil.getObjectToString(returnDataTemplete));
        }finally {
            /*try {
                if(con!=null)
                con.close();
            } catch (SQLException throwables) {
                throw new RuntimeException(throwables);
            }*/
        }
    }
}
