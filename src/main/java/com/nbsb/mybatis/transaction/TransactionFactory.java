package com.nbsb.mybatis.transaction;

import com.nbsb.mybatis.session.TransactionIsolationLevel;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 事务工厂
 * @create: 2024/3/24 14:40
 */
public interface TransactionFactory {
    /**
     * 根据 Connection 创建 Transaction
     * @param conn 现有数据库连接
     * @return Transaction
     */
    Transaction newTransaction(Connection conn);

    /**
     * 根据数据源和事务隔离级别创建 Transaction
     * @param dataSource DataSource to take the connection from
     * @param level 所需的隔离级别
     * @param autoCommit 所需的自动提交
     * @return Transaction
     */
    Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);

}
