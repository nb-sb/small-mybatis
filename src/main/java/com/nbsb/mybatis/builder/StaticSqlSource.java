package com.nbsb.mybatis.builder;

import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.ParameterMapping;
import com.nbsb.mybatis.mapping.SqlSource;
import com.nbsb.mybatis.session.Configuration;

import java.util.List;
/**
* @author: Wanghaonan @戏人看戏
* @description: 静态SQL源码
* @create: 2024/4/7 10:55
*/
public class StaticSqlSource implements SqlSource {

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
//        BoundSql boundSql = new BoundSql(sql,parameterMappings,);
//        return boundSql;
        return null;
    }
}
