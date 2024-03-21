package com.nbsb.mybatis.session.defaults;

import com.nbsb.mybatis.binding.MapperRegistry;
import com.nbsb.mybatis.session.SqlSession;
/**
* @author: Wanghaonan @戏人看戏
* @description: sqlsession 实现类
* @create: 2024/3/21 21:56
*/
public class DefaultSqlSession implements SqlSession {
    /**
     * 映射器注册机
     */
    private MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你被代理了！" + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return (T) ("你被代理了！" + "方法：" + statement + " 入参：" + parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }

}
