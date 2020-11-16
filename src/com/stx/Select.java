package com.stx;

import com.stx.Dao.JDBCUtil;

public class Select {
    public static void main(String[] args) {
        JDBCUtil jdbcUtil = new JDBCUtil();
        jdbcUtil.executeQuery("select * from users");
    }

}
