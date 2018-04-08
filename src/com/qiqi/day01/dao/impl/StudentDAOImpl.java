package com.qiqi.day01.dao.impl;

import com.qiqi.day01.dao.IStudentDAO;
import com.qiqi.day01.dao.domain.Student;
import com.qiqi.day01.dao.util.JdbcTemplete;
import com.qiqi.day01.dao.util.JdbcUtil;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO {
    @Override
    public void save(Student student) throws Exception {
        String sql = "INSERT INTO student (name, age) VALUES (?, ?)";
        Object[] objs = {student.getName(), student.getAge()};
        JdbcTemplete.update(sql, objs);
    }

    @Override
    public void update(Student student) throws Exception {
        String sql = "UPDATE student SET name = ?, age = ? WHERE id = ?";
        Object[] objs = {student.getName(), student.getAge(), student.getId()};
        JdbcTemplete.update(sql, objs);
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE FROM student WHERE id = ?";
        JdbcTemplete.update(sql, id);
    }

    @Override
    public Student get(Long id) throws Exception {
        String sql = "SELECT * FROM student WHERE id = ?";
        List<Student> list = JdbcTemplete.query(sql, new StudentHandleImpl(), id);
        return list.size() != 0 ? list.get(0) : null;
    }

    @Override
    public List<Student> listAll() throws Exception {
        String sql = "SELECT * FROM student";
        List<Student> list = JdbcTemplete.query(sql, new BeanListHandleImpl<>(Student.class));
        return list;
    }
}
