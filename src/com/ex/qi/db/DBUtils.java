package com.ex.qi.db;


import java.sql.*;

/**
 * Created by note on 2016/3/18.
 */
public class DBUtils {
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SUNLINE";
    String user = "sa";
    String pass = "tiger";

    public Connection getConn(){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            if (null != conn){
                //System.out.println("数据库连接成功");
            }
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("SQL Server 驱动程序没有找到");
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("数据库连接失败");
        }
        return conn;
    }

    public void closeConn(Connection conn,PreparedStatement pStatement,ResultSet resultSet){
        try {
            if (resultSet != null){
                resultSet.close();
            }
            if (null != pStatement){
                pStatement.close();
            }
            if (null != conn){
                conn.close();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("数据库关闭失败");
        }
    }
    public void closeConn(Connection conn,PreparedStatement pStatement){
        try {
            if (null != pStatement){
                pStatement.close();
            }
            if (null != conn){
                conn.close();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("数据库关闭失败");
        }
    }

}
