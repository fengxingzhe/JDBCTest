package com.qiqi.day01.dao.impl;

import com.qiqi.day01.dao.IResultSetHandle;
import com.qiqi.day01.dao.domain.Student;

import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class StudentHandleImpl implements IResultSetHandle<List<Student>> {
    @Override
    public List<Student> handle(ResultSet resultSet) throws Exception{
        LinkedList<Student> list = new LinkedList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Integer age = resultSet.getInt("age");
            list.add(new Student(id, name, age));
        }
        return list;
    }
}
