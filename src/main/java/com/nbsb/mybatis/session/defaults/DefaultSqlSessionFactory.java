package com.nbsb.mybatis.session.defaults;

import com.nbsb.mybatis.session.Configuration;
import com.nbsb.mybatis.session.SqlSession;
import com.nbsb.mybatis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }

}
