package com.qiqi.day01.dao.impl;

import com.qiqi.day01.dao.IResultSetHandle;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandleImpl<T> implements IResultSetHandle<List<T>> {
    private Class<T> clz;
    public BeanListHandleImpl(Class<T> clz) {
        this.clz = clz;
    }
    @Override
    public List<T> handle(ResultSet resultSet) throws Exception {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            T t = this.clz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(this.clz, Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String columnName = pd.getName();
                Object columnValue = resultSet.getObject(columnName);
                Method writeMethod = pd.getWriteMethod();
                writeMethod.invoke(t, columnValue);
            }
            list.add(t);
        }
        return list;
    }
}
