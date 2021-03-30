package top.wnyeagle.LockWebServer.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 该过滤器主要过滤管理员权限的路径
 * @ClassName AdministratorFilter
 * @Author wny
 * @Date 2021/3/26 21:53
 * @Version 1.0
 */

public class AdministratorFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String authority = (String) req.getSession().getAttribute("authority");
        if (authority.equals("manager")){
            chain.doFilter(request,response);
        }else{
            resp.sendRedirect("/LockWebServer/pages/phone/phoneLogin.html");
        }
    }

    @Override
    public void destroy() {

    }
}
