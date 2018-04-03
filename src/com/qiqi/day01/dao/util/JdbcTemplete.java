package com.qiqi.day01.dao.util;

import com.qiqi.day01.dao.IResultSetHandle;
import com.qiqi.day01.dao.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class JdbcTemplete {
    private JdbcTemplete(){}

    public static void update (String sql, Object...params) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JdbcUtil.getConnection();
            ps = connection.prepareStatement(sql);
            int index = 1;
            for (Object param : params) {
                ps.setObject(index, param);
                index ++;
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(connection, ps, null);
        }
    }

    public static <T>T query(String sql, IResultSetHandle<T> handle, Object...params) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getConnection();
            ps = connection.prepareStatement(sql);
            int index = 1;
            for (Object param : params) {
                ps.setObject(index, param);
                index ++;
            }
            resultSet = ps.executeQuery();
            T list = handle.handle(resultSet);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtil.close(connection, ps, resultSet);
        }
    }
}
