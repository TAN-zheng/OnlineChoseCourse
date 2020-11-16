package com.stx.Dao;

import java.sql.*;
import java.util.ResourceBundle;

public class JDBCUtil {
    //构造方法私有化，使得这个类不能被实例化创建。直接通过类名访问。
    //1.注册驱动
    //静态代码块,类预加载的时候就注册驱动了。

    public static Connection getConnection() {

        ResourceBundle bundle = ResourceBundle.getBundle("com/stx/Dao/jdbc");
        String driver=bundle.getString("driver");
        String url=bundle.getString("url");
        String user=bundle.getString("user");
        String password=bundle.getString("password");
        try {
            //1、注册驱动
            Class.forName(driver);
            //2、获取连接
           return DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            //捕获异常
            ex.printStackTrace();
        }
        //这是一个有返回值的构造方法，返回conn给调用它的方法。
      return null;
    }

    /*关闭资源的构造方法，断开连接。防止资源浪费。
     *
     * */
    public static void closeConnection(ResultSet resultSet, PreparedStatement stmt, Connection conn) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*这是一个执行executeUpdate方法的方法抽取。
     *原因:数据库有二个类型。
     * 数据查询语言DQL，数据操纵语言DML，数据定义语言DDL，数据控制语言DCL。
     * 一个是executeUpdate：(DML，DDL)
     * 一个是executeQuery:(DQL)
     *DML：数据，插，删，改。
     * DDL：表操作。
     * DQL：查询。
     * */
    public static int executeUpdate(String sql,Object... args) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //1、先获取连接
            conn = getConnection();
            //2、使用连接，预编译一个SQL语句对象方法。
            stmt = conn.prepareStatement(sql);
            //4、通过一个for循环来一行每一列的遍历输入。
            for (int i = 0; i <args.length ; i++) {
                //给参数列表赋值。
                stmt.setObject(i+1,args[i]);
            }
            return stmt.executeUpdate();

            //3、调用executeUpdate方法执行SQL语句。
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeConnection(null, stmt, conn);
        }

    }
public static ResultSet executeQuery(String sql){
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    try {
        //1、先获取连接
        conn = getConnection();
        //2、使用连接，预编译一个SQL语句。
        stmt = conn.prepareStatement(sql);
        //4、通过一个for循环来一行每一列的遍历输入。
        resultSet=stmt.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("userName"));
            System.out.println(resultSet.getString("userPassword"));
        }

    } catch (SQLException e) {
        e.printStackTrace();

    } finally {
        closeConnection(null, stmt, conn);
    }
    return resultSet;
}

}
