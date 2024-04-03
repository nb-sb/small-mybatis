package com.nbsb.mybatis.transaction.jdbc;

import com.nbsb.mybatis.session.TransactionIsolationLevel;
import com.nbsb.mybatis.transaction.Transaction;
import com.nbsb.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: JdbcTransaction 工厂，用于创建事务
 * @create: 2024/3/24 14:59
 */
public class JdbcTransactionFactory implements TransactionFactory {

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }
}
