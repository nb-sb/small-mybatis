package com.nbsb.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;
/**
* @author: Wanghaonan @戏人看戏
* @description: 事务接口，用于操作jdbc事务，将jdbc提供的方法进行封装
* @create: 2024/3/24 14:43
*/
public interface Transaction {
    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

}
