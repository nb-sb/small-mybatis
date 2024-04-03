package com.nbsb.mybatis.session.defaults;

import com.nbsb.mybatis.binding.MapperRegistry;
import com.nbsb.mybatis.executor.Executor;
import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.Environment;
import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.session.Configuration;
import com.nbsb.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
* @author: Wanghaonan @戏人看戏
* @description: sqlsession 实现类
* @create: 2024/3/21 21:56
*/
public class DefaultSqlSession implements SqlSession {
    /**
     * 映射器注册机
     */
    private Configuration configuration;
    private Executor executor;
    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你被代理了！" + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        try {
            MappedStatement mappedStatement = configuration.getMapSqlByStatements(statement);
            List<T> list = executor.query(mappedStatement, parameter, null, mappedStatement.getBoundSql());
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @Des 将jdbc中的返回值装入到javabean中
     * @Date 2024/3/24 22:07
     */
    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 每次遍历行值
            while (resultSet.next()) {
                //获取这个类的对象
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    //调用set函数
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(setMethod, Date.class);
                    } else {
                        Class<?> aClass = value.getClass();
                        method = clazz.getMethod(setMethod, aClass);
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapperRegistry().getMapper(type, this);
    }

}
