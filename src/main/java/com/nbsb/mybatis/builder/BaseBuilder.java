package com.nbsb.mybatis.builder;

import com.nbsb.mybatis.session.Configuration;
import com.nbsb.mybatis.type.TypeAliasRegistry;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 构建器的基类，用于构建出xml的配置文件的类
 * @create: 2024/3/22 13:08
 */
public abstract class BaseBuilder {
    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }

    public Configuration getConfiguration() {
        return configuration;
    }
    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }
    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}
