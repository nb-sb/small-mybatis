package com.nbsb.mybatis.executor.resultset;

import com.nbsb.mybatis.session.ResultHandler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 结果处理器，处理sql语句返回结果
 * @create: 2024/4/2 14:49
 */
public interface ResultSetHandler  {
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;
}
