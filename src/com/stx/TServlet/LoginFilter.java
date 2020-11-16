package com.stx.TServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//                                         ServletRequest是HttpServletRequest的父类。
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getServletPath().contains("login.jsp") || request.getServletPath().contains("login")){
//                                  如果满足条件就让其跳转到首页。
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
                                /*
                                 * 详细思路：session在LoginServlet里面存储了一个信息，在创建的对象能获取到里面存储的信息，根据这个性质来判断是否已经登录了。
                                 * 由于已经有session存在信息了，会话期间通过这个对象可以取到相应的值。
                                 */
        HttpSession session =request.getSession();
//                               获取session里面的信息。 Object类型需要转换。
        String isLogin = (String)session.getAttribute("isLogin");
//                               判断是否已经登录。通过现在存入的ok与在LoginServlet通过验证登录的状态比较。
        if ("ok".equals(isLogin)){
//                               如果是就放行。
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
//                               如果没有验证登录，换句话就是，如果这个会话终止过，就没有LoginServlet里面的session的ok。那就跳转到登录界面重新登录。
                response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
