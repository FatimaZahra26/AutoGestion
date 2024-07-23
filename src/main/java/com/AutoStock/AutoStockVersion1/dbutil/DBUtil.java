package com.AutoStock.AutoStockVersion1.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection connection=null;

    public static Connection getConnection() throws SQLException {
        if(connection!=null) {
            return connection;
        }else {
            String driver="com.mysql.cj.jdbc.Driver";
            String url="jdbc:mysql://localhost:3306/AutoStock?useSSl=false";
            String username="root";
            String password="";
            try {
                Class.forName(driver);
                connection= DriverManager.getConnection(url,username,password);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return connection;
    }
}
