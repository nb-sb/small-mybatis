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
* @description: 简单语句处理，没有查询参数的那些
* @create: 2024/4/3 15:21
*/
public class SimpleStatementHandler extends BaseStatementHandler{


    protected SimpleStatementHandler(Object parameterObject, ResultHandler resultSetHandler, BoundSql boundSql, Executor executor, MappedStatement mappedStatement) {
        super(parameterObject, resultSetHandler, boundSql, executor, mappedStatement);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {

    }

    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        String sql = boundSql.getSql();
        statement.execute(sql);
        return resultSetHandler.handleResultSets(statement);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        return 0;
    }
}
