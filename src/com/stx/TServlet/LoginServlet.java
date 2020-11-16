package com.stx.TServlet;

import com.stx.Dao.UserDaoImpl;
import com.stx.entity.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //                                    获取用户名
       String username = req.getParameter("username");
//                                        获取密码
      String password =  req.getParameter("password");
//                                           创建一个session对象，该对象存储的信息，在对话期间都存在。
        HttpSession session =req.getSession();
        System.out.println(username);
//                                               验证密码和账户
/*                                            if ("Jack".equals(username) && "111111".equals(password)){
                                    //                                           这个对象的信息在对话期间，也就是网页没被关闭期间，都能通过Get获取到这个键值对。
                                                session.setAttribute("isLogin","ok");
                                                resp.sendRedirect(req.getContextPath()+"/OnlineChoseCourse.jsp");
                                                return;
                                            }else {
                                                resp.sendRedirect(req.getContextPath()+"/login.jsp");
                                            }*/
        UserDaoImpl userDao = new UserDaoImpl();
        Users user =userDao.getUsersByUsernameAndPassword(username,password);
        if (user.getUser_name() !=null && user.getUser_password() !=null){
            session.setAttribute("isLogin","ok");
            resp.sendRedirect(req.getContextPath()+"/OnlineChoseCourse.jsp");
            return;
        }else {
        resp.sendRedirect(req.getContextPath()+"/login.jsp");
        }
    }


}
