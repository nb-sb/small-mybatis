package com.nbsb.mybatis.session;

import com.nbsb.mybatis.binding.MapperRegistry;
import com.nbsb.mybatis.datasource.druid.DruidDataSourceFactory;
import com.nbsb.mybatis.datasource.pooled.PooledDataSourceFactory;
import com.nbsb.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.nbsb.mybatis.executor.Executor;
import com.nbsb.mybatis.executor.SimpleExecutor;
import com.nbsb.mybatis.executor.resultset.DefaultResultSetHandler;
import com.nbsb.mybatis.executor.resultset.ResultSetHandler;
import com.nbsb.mybatis.executor.statement.PreparedStatementHandler;
import com.nbsb.mybatis.executor.statement.StatementHandler;
import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.Environment;
import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.scripting.LanguageDriverRegistry;
import com.nbsb.mybatis.scripting.XMLLanguageDriver;
import com.nbsb.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.nbsb.mybatis.type.TypeAliasRegistry;
import com.nbsb.mybatis.transaction.Transaction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author: Wanghaonan @戏人看戏
 * @description: 配置类，
 * @create: 2024/3/22 12:59
 */
public class Configuration {
    /**
     * 环境
     */
    protected Environment environment;
    /**
     * 映射注册机
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry();

    /**
     * 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();
    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();
    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();


    protected final Set<String> loadedResources = new HashSet<>();


    public Configuration() {
        //注册jdbc工厂
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        //注册德鲁伊工厂
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        //注册无池化数据源工厂
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
    }



    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }
    public Environment getEnvironment() {
        return environment;
    }
    public MapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }

    public void setMapperRegistry(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    public Map<String, MappedStatement> getMappedStatements() {
        return mappedStatements;
    }

    public MappedStatement getMapSqlByStatements(String sm) {
        MappedStatement mappedStatement = mappedStatements.get(sm);
        return mappedStatement;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public void addLoadedResource(String resource) {
        loadedResources.add(resource);
    }
    public LanguageDriverRegistry getLanguageRegistry() {
        return languageRegistry;
    }

    /**
     * 创建语句处理器
     */
    public StatementHandler newStatementHandler(Executor executor, MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PreparedStatementHandler(parameter, resultHandler, boundSql,executor,ms);
    }
    /**
     * 创建结果集处理器
     */
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultSetHandler(boundSql);
    }
    /**
     * 生产执行器
     */
    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this, transaction);
    }

    public void addMappedStatement(MappedStatement mappedStatement) {
        mappedStatements.put(mappedStatement.getId(), mappedStatement);
    }
}
