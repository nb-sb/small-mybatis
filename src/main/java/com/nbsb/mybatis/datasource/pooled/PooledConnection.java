package com.nbsb.mybatis.datasource.pooled;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
* @author: Wanghaonan @戏人看戏
* @description: 池化代理的连接，用于过滤close等真实的连接方法
* @create: 2024/3/28 11:15
*/
public class PooledConnection implements InvocationHandler {
    private static final String CLOSE = "close";
    private boolean valid;//连接状态
    //数据库连接池，将连接统一管理
    private PooledDataSource dataSource;
    // 真实的Connection连接
    private Connection realConnection;
    // 代理的Connection连接
    private Connection proxyConnection;

    //这里实际上就是封装的代理connection，实际上还是执行的真实的代理连接执行的参数，
    // 这里用于过滤用户输入的close的方法，因为这里用的是连接池，直接过滤后加入池中
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        // 如果是调用 CLOSE 关闭链接方法，则将链接加入连接池中，并返回null
        if (CLOSE.hashCode() == methodName.hashCode() && CLOSE.equals(methodName)) {
            dataSource.pushConnection(this);
            return null;
        } else {
            if (!Object.class.equals(method.getDeclaringClass())) {
                // 除了toString()方法，其他方法调用之前要检查connection是否还是合法的,不合法要抛出SQLException
                checkConnection();
            }
            // 其他方法交给connection去调用
            return method.invoke(realConnection, args);
        }
    }
    private void checkConnection() throws SQLException {
        if (!valid) {
            throw new SQLException("Error accessing PooledConnection. Connection is invalid.");
        }
    }
    public void invalidate() {
        valid = false;
    }

    public boolean isValid() {
        return valid && realConnection != null && dataSource.pingConnection(this);
    }
}
