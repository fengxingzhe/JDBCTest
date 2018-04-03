package com.qiqi.day01.dao;

import com.qiqi.day01.dao.domain.Student;

import java.sql.ResultSet;
import java.util.List;

public interface IResultSetHandle<T> {

    T handle(ResultSet resultSet) throws Exception;

}
