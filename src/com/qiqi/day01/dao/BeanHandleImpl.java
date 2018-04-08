package com.qiqi.day01.dao;

import com.qiqi.day01.dao.impl.BeanListHandleImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public class BeanHandleImpl<T> implements IResultSetHandle<T> {
    private Class<T> clz;
    public BeanHandleImpl (Class<T> clz) {
        this.clz = clz;
    }
    @Override
    public T handle(ResultSet resultSet) throws Exception {
        T t = this.clz.newInstance();
        if (resultSet.next()) {
            BeanInfo beanInfo = Introspector.getBeanInfo(this.clz, Object.class);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                String columnName = pd.getName();
                Object columnValue = resultSet.getObject(columnName);
                Method writeMethod = pd.getWriteMethod();
                writeMethod.invoke(t, columnValue);
            }
        }
        return null;
    }
}
