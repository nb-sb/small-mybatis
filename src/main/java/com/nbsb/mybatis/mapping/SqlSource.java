package com.nbsb.mybatis.mapping;

public interface SqlSource {
    BoundSql getBoundSql(Object parameterObject);
}
