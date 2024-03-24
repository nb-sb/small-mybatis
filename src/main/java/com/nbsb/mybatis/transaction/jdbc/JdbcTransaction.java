package com.nbsb.mybatis.transaction.jdbc;

import com.nbsb.mybatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
/**
* @author: Wanghaonan @戏人看戏
* @description: JDBC 事务，直接利用 JDBC 的 commit、rollback。依赖于数据源获得的连接来管理事务范围
* @create: 2024/3/24 14:55
*/
public class JdbcTransaction implements Transaction {

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public void commit() throws SQLException {

    }

    @Override
    public void rollback() throws SQLException {

    }

    @Override
    public void close() throws SQLException {

    }
}
