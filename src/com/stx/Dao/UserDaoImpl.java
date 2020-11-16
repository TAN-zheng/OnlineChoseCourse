package com.stx.Dao;

import com.stx.entity.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {

    public Users getUsersByUsernameAndPassword(String username, String password) {
        Users user=null;
        Connection conn=null;
        PreparedStatement stmt=null;
        ResultSet rs=null;
        String sql="SELECT username,userpassword from users where username =? and userpassword =?";
        try {
            conn =JDBCUtil.getConnection();
            stmt =conn.prepareStatement(sql);
            stmt.setString(1,username);
            stmt.setString(2,password);
            rs=stmt.executeQuery();
            if (rs.next()){
                user = new Users();
                user.setUser_name(rs.getString("userName"));
                user.setUser_password(rs.getString("userPassword"));
                System.out.println(rs.getString("username"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtil.closeConnection(rs,stmt,conn);
        }
        return null;
    }
}
