package com.nbsb.mybatis.executor;

import com.nbsb.mybatis.executor.statement.StatementHandler;
import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.session.Configuration;
import com.nbsb.mybatis.session.ResultHandler;
import com.nbsb.mybatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SimpleExecutor extends BaseExecutor{
    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        try {
            StatementHandler handler = configuration.newStatementHandler(this, ms, parameter, resultHandler, boundSql);
            Connection connection = transaction.getConnection();
            Statement stmt = handler.prepare(connection);
            handler.parameterize(stmt);
            return handler.query(stmt, resultHandler);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
