package com.nbsb.mybatis;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 映射代理类
 * @create: 2024/3/21 10:27
 */
public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398559729838L;
    private Map<String , String> sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(Map<String, String> sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //判断当前方法是否是调用的equals方法，如果是的话就执行方法本身的内容
        if (Object.class.equals(method.getDeclaringClass())) {
            //通过反射机制调用了当前对象的相应方法，并将原始参数传递给该方法，最终返回方法调用的结果。
            return method.invoke(this, args);
        } else {
//            System.out.println(sqlSession.get(mapperInterface.getName() + "." + method.getName()));
            //获取类路径和方法名进行拼接获得调用内容
            return "你被代理了" + sqlSession.get(mapperInterface.getName() + "." + method.getName());
        }
    }
}
