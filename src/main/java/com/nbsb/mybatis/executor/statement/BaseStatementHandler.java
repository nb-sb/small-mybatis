package com.nbsb.mybatis.executor.statement;

import com.nbsb.mybatis.executor.Executor;
import com.nbsb.mybatis.executor.resultset.ResultSetHandler;
import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.session.Configuration;
import com.nbsb.mybatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 语句处理抽象类，将公用的方法抽出来
 * @create: 2024/4/3 09:31
 */
public abstract class BaseStatementHandler implements StatementHandler{
    protected final Configuration configuration;
    protected final Object parameterObject;
    protected final ResultSetHandler resultSetHandler;
    protected BoundSql boundSql;
    protected final Executor executor;
    protected final MappedStatement mappedStatement;
    protected BaseStatementHandler(Object parameterObject, ResultHandler resultSetHandler, BoundSql boundSql, Executor executor, MappedStatement mappedStatement) {
        this.configuration = mappedStatement.getConfiguration();
        this.parameterObject = parameterObject;

        this.boundSql = boundSql;
        this.executor = executor;
        this.mappedStatement = mappedStatement;
        this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, boundSql);
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        Statement statement = null;
        try {
            // 实例化 Statement
            statement = instantiateStatement(connection);
            // 参数设置，可以被抽取，提供配置
            statement.setQueryTimeout(350);
            statement.setFetchSize(10000);
            return statement;
        } catch (Exception e) {
            throw new RuntimeException("Error preparing statement.  Cause: " + e, e);
        }
    }
    //可以方便继承者进行重写
    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;

}
