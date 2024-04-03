package com.nbsb.mybatis.transaction.jdbc;

import com.nbsb.mybatis.session.TransactionIsolationLevel;
import com.nbsb.mybatis.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
/**
* @author: Wanghaonan @戏人看戏
* @description: JDBC 事务，直接利用 JDBC 的 commit、rollback。依赖于数据源获得的连接来管理事务范围
* @create: 2024/3/24 14:55
*/
public class JdbcTransaction implements Transaction {
    protected Connection connection;
    protected DataSource dataSource;
    protected TransactionIsolationLevel level = TransactionIsolationLevel.NONE;
    protected boolean autoCommit;

    public JdbcTransaction(Connection connection) {
        this.connection = connection;
    }

    public JdbcTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        try {
            this.connection = dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        this.dataSource = dataSource;
        this.level = level;
        this.autoCommit = autoCommit;
    }
    @Override
    public Connection getConnection() throws SQLException {
        connection.setTransactionIsolation(level.getLevel());
        connection.setAutoCommit(autoCommit);
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.close();
        }
    }
}
