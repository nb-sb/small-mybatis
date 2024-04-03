package com.nbsb.mybatis.executor.statement;

import com.nbsb.mybatis.executor.Executor;
import com.nbsb.mybatis.executor.resultset.DefaultResultSetHandler;
import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.session.ResultHandler;

import java.sql.*;
import java.util.List;

/**
* @author: Wanghaonan @戏人看戏
* @description: 语句处理器
* @create: 2024/4/3 13:50
*/
public class PreparedStatementHandler extends BaseStatementHandler {


    public PreparedStatementHandler(Object parameterObject, ResultHandler resultSetHandler, BoundSql boundSql, Executor executor, MappedStatement mappedStatement) {
        super(parameterObject, resultSetHandler, boundSql, executor, mappedStatement);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        String sql = boundSql.getSql();
        return connection.prepareStatement(sql);
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        PreparedStatement ps = (PreparedStatement) statement;
        ps.setLong(1, Long.parseLong(((Object[]) parameterObject)[0].toString()));
    }
    /**
     * @Des 已经在init中准备好sql了所以这里可以直接进行执行
     */
    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        //将执行后的处理结果进行封装返回
        List<E> es = resultSetHandler.handleResultSets(ps);
        return es;
    }

    @Override
    public int update(Statement statement) throws SQLException {
        return 0;
    }


}
