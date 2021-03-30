package top.wnyeagle.LockWebServer.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description  该过滤器的作用是过滤某一些必须登录才能访问的资源
 * @ClassName AuthorityFilter
 * @Author wny
 * @Date 2021/3/16 22:15
 * @Version 1.0
 */

public class AuthorityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest re1 = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (re1.getSession().getAttribute("userName")==null){
            resp.sendRedirect("/LockWebServer/pages/phone/phoneLogin.html");
        }else{
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
