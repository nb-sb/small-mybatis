package com.nbsb.mybatis.session.defaults;

import com.nbsb.mybatis.executor.Executor;
import com.nbsb.mybatis.mapping.Environment;
import com.nbsb.mybatis.session.Configuration;
import com.nbsb.mybatis.session.SqlSession;
import com.nbsb.mybatis.session.SqlSessionFactory;
import com.nbsb.mybatis.session.TransactionIsolationLevel;
import com.nbsb.mybatis.transaction.Transaction;
import com.nbsb.mybatis.transaction.TransactionFactory;

import java.sql.SQLException;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Transaction tx = null;
        try {
            final Environment environment = configuration.getEnvironment();
            TransactionFactory transactionFactory = environment.getTransactionFactory();
            tx = transactionFactory.newTransaction(configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
            // 创建执行器
            final Executor executor = configuration.newExecutor(tx);
            // 创建DefaultSqlSession
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            try {
                assert tx != null;
                tx.close();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException("Error opening session.  Cause: " + e);
        }
    }

}
