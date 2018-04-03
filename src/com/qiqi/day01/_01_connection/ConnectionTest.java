package com.qiqi.day01._01_connection;

import com.mysql.jdbc.Driver;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.sql.*;

public class ConnectionTest {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection
                    = DriverManager.getConnection("jdbc:mysql://localhost:3306/testqiqi?useSSL=false"
                                                , "root"
                                                , "admin");
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM test WHERE id = '4'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
