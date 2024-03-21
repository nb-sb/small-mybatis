package com.nbsb.mybatis.binding;

import com.nbsb.mybatis.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 映射器代理工厂
 * @create: 2024/3/21 10:26
 */
public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    //需要创建的接口的类，比如IUserDao.class，用于代理调用其中方法
    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public  T newInstance(SqlSession sqlSession) {
        //创建mapperProxy代理，因为会调用mapperProxy中的内容
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(
                mapperInterface.getClassLoader(),
                new Class[]{mapperInterface},
                mapperProxy);
    }
}
