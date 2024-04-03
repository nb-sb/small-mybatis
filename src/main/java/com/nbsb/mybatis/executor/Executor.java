package com.nbsb.mybatis.executor;

import com.nbsb.mybatis.session.ResultHandler;
import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 执行器
 * @create: 2024/4/2 15:39
 */
public interface Executor {
        ResultHandler NO_RESULT_HANDLER = null;

        <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);

        Transaction getTransaction();

        void commit(boolean required) throws SQLException;

        void rollback(boolean required) throws SQLException;

        void close(boolean forceRollback);
}
