package com.qiqi.day01.dao.test;

import com.qiqi.day01.dao.IStudentDAO;
import com.qiqi.day01.dao.domain.Student;
import com.qiqi.day01.dao.impl.StudentDAOImpl;
import com.qiqi.day01.dao.util.JdbcUtil;
import org.junit.Test;
import org.junit.internal.runners.TestMethod;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAOTest {

    private IStudentDAO dao = new StudentDAOImpl();

    @Test
    public void save() throws Exception {
        for (int i = 0; i < 20; i++) {
            dao.save(new Student("qiqi" + i, i));
        }
    }

    @Test
    public void update() throws Exception {
        dao.update(new Student(Long.valueOf("1"), "haha", 23));
    }

    @Test
    public void delete() throws Exception {
        dao.delete(Long.valueOf("1"));
    }

    @Test
    public void get() throws Exception {
        Student student = dao.get(Long.valueOf("3"));
        System.out.println(student);
    }

    @Test
    public void listAll() throws Exception {
        System.out.println(dao.listAll().toString());
    }

    @Test
    public void testTx() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select * from account where name = ? and balance = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "西门官人");
            preparedStatement.setBigDecimal(2, new BigDecimal(10000));
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("余额不足");
            }
            sql = "UPDATE account SET balance = balance - ? WHERE name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, BigDecimal.valueOf(10000));
            preparedStatement.setString(2, "西门官人");
            preparedStatement.executeUpdate();

            sql = "UPDATE account SET balance = balance + ? WHERE name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBigDecimal(1, BigDecimal.valueOf(10000));
            preparedStatement.setString(2, "东方姑娘");
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("提交");
        } catch (Exception e) {
            try {
                System.out.println("回滚");
                connection.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            System.out.println("关闭");
            JdbcUtil.close(connection, preparedStatement, resultSet);
        }
    }

    @Test
    public void testBatch() {
        Connection connection = null;
        PreparedStatement psmt = null;
        long start;
        try {
            start = System.currentTimeMillis();
            connection = JdbcUtil.getConnection();
            String sql = "INSERT INTO student (name, age) VALUES (? , 1)";
            psmt = connection.prepareStatement(sql);
            for (int i = 0; i < 1000; i++) {
                psmt.setString(1, "qiqi" + i);
                psmt.addBatch();
                if (i % 100 == 0) {
                    psmt.executeBatch();
                    psmt.clearBatch();
                    psmt.clearParameters();
                }
            }
            System.out.println("end---"+ (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(connection, psmt, null);
        }
    }
}