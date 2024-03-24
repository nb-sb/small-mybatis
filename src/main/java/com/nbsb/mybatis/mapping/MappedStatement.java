package com.nbsb.mybatis.mapping;

import com.nbsb.mybatis.session.Configuration;

import java.util.Map;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 映射类，用于映射xml中sql语句等内容
 * @create: 2024/3/22 13:01
 */
public class MappedStatement {
    private String id;
    private SqlCommandType sqlCommandType;
    private BoundSql boundSql;
//    private String sql;
//    private String parameterType;
//    private String resultType;
//    private Map<Integer, String> parameter;

    MappedStatement() {
        // constructor disabled
    }
    /**
     * 使用builder进行构建
     */
    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder( String id, SqlCommandType sqlCommandType, BoundSql boundSql) {
            mappedStatement.id = id;
            mappedStatement.sqlCommandType = sqlCommandType;
            mappedStatement.boundSql = boundSql;
        }

        public MappedStatement build() {
            assert mappedStatement.id != null;
            return mappedStatement;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public BoundSql getBoundSql() {
        return boundSql;
    }

    public void setBoundSql(BoundSql boundSql) {
        this.boundSql = boundSql;
    }
}
