package com.nbsb.mybatis.binding;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.nbsb.mybatis.session.SqlSession;
import com.nbsb.mybatis.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 这个是一个映射器注册类，可以自动扫描对应dao路径下的对象进行注册进来
 * @create: 2024/3/21 14:42
 */
public class MapperRegistry {

    private Logger logger = LoggerFactory.getLogger(MapperRegistry.class);
    /**
     * 将已添加的映射器代理加入到 HashMap
     */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    //1.传入一个包的地址将其mapperbean类扫描进来，将扫描的类调用注册进行注册
    public void addMappers(String packageName) {
        Set<Class<?>> classes = ClassUtil.getClasses(packageName);
        for (Class<?> mapperClass : classes) {
            logger.info("扫描到 mapperClass:{}", mapperClass.getName());
            addMapper(mapperClass);
        }
    }

    //2.注册类
    public <T> void addMapper(Class<T> type) {
        /* Mapper 必须是接口才会注册 */
        if (type.isInterface()) {
            if (hasMapper(type)) {
                // 如果重复添加了，报错
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            // 注册映射器代理工厂
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    //判断类型
    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    /**
     * @Des 使用class类型和sqlsession进行获取mapper对象
     * @Date 2024/3/21 21:46
     */
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }
}
