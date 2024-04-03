package com.nbsb.mybatis.executor.statement;

import com.nbsb.mybatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 语句处理接口方法
 * @create: 2024/4/3 10:04
 */
public interface StatementHandler {
    /** 准备语句 */
    Statement prepare(Connection connection) throws SQLException;

    /** 参数化 */
    void parameterize(Statement statement) throws SQLException;

    /**
     * 执行查询
     */
    <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;
    /** 执行更新 */
    int update(Statement statement) throws SQLException;
}
