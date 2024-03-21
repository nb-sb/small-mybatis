package com.nbsb.mybatis.session.defaults;

import com.nbsb.mybatis.binding.MapperRegistry;
import com.nbsb.mybatis.session.SqlSession;
import com.nbsb.mybatis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }

}
