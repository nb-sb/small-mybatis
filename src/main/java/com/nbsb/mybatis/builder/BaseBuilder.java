package com.nbsb.mybatis.builder;

import com.nbsb.mybatis.session.Configuration;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 构建器的基类，用于构建出xml的配置文件的类
 * @create: 2024/3/22 13:08
 */
public class BaseBuilder {
    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
