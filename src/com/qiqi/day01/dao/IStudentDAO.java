package com.qiqi.day01.dao;

import com.qiqi.day01.dao.domain.Student;

import java.util.List;

public interface IStudentDAO {
    /**
     * 保存
     * @param student
     */
    void save(Student student) throws Exception;

    /**
     * 编辑
     * @param student
     */
    void update(Student student) throws Exception;

    /**
     * 删除
     * @param id
     */
    void delete(Long id) throws Exception;

    /**
     * 查询
     * @param id
     * @return
     */
    Student get(Long id) throws Exception;

    /**
     * 查询所有
     * @return
     */
    List<Student> listAll() throws Exception;
}
