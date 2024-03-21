package com.nbsb.mybatis;

import com.nbsb.mybatis.binding.MapperProxyFactory;
import com.nbsb.mybatis.binding.MapperRegistry;
import com.nbsb.mybatis.dao.IHomeInfoDao;
import com.nbsb.mybatis.dao.IUserDao;
import com.nbsb.mybatis.session.SqlSession;
import com.nbsb.mybatis.session.SqlSessionFactory;
import com.nbsb.mybatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


public class SmallMybatisApplicationTests {
    private Logger logger = LoggerFactory.getLogger(SmallMybatisApplicationTests.class);

    @Test
    public void TestAgent() {
        IUserDao o = (IUserDao) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class},
                ((proxy, method, args) -> "已经被代理"+"方法名："+method.getName()+" 参数数量："+args.length)
        );
        System.out.println(o.queryUserName("1123","whn"));


        IUserDao o1 = (IUserDao) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class},
                ((proxy, method, args) -> 10)
        );
        System.out.println(o1.queryUserAge("1","whn"));
    }

    @Test
    public void test_class_agent() {
        //当前是使用硬编码方式将起接口对象进行代理实现的
        //当前需要手动添加MapperProxy，下一阶段可以将其修改为自动扫描包路径进行注册
        MapperProxyFactory<IUserDao> factory = new MapperProxyFactory<>(IUserDao.class);

        Map<String, String> sqlSession = new HashMap<>();
        sqlSession.put("com.nbsb.mybatis.dao.IUserDao.queryUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
        sqlSession.put("com.nbsb.mybatis.dao.IUserDao.queryUserAge", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");
//        IUserDao userDao = factory.newInstance(sqlSession);

//        String res = userDao.queryUserName("10001");
//        logger.info("测试结果：{}", res);

    }

    @Test
    public void test_1() {
        Map<String,String> map = new HashMap<>();
        map.put("123", "23");
        System.out.println(map.containsKey("123"));
    }

    @Test
    public void test_class_agent_2() {
        // 1. 注册 Mapper
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("com.nbsb.mybatis.dao");
//         2. 从 SqlSession 工厂获取 Session
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 3. 获取映射器对象
        IHomeInfoDao dao = sqlSession.getMapper(IHomeInfoDao.class);
        // 4. 进行调用方法
        String s = dao.queryHomeName("1");
        logger.info("res1: {}", s);

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        String res = mapper.queryUserName("1", "whn");
        logger.info("res2: {}", res);
    }
}
