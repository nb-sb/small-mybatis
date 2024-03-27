package com.nbsb.mybatis.test;

import com.alibaba.fastjson.JSON;
import com.nbsb.mybatis.binding.MapperProxyFactory;
import com.nbsb.mybatis.io.Resources;
import com.nbsb.mybatis.session.SqlSessionFactoryBuilder;
import com.nbsb.mybatis.test.dao.IHomeInfoDao;
import com.nbsb.mybatis.test.dao.IUserDao;
import com.nbsb.mybatis.session.SqlSession;
import com.nbsb.mybatis.session.SqlSessionFactory;
import com.nbsb.mybatis.test.po.User;
import org.dom4j.DocumentException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
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
        sqlSession.put("com.nbsb.mybatis.test.dao.IUserDao.queryUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
        sqlSession.put("com.nbsb.mybatis.test.dao.IUserDao.queryUserAge", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");
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
//        // 1. 注册 Mapper
//        MapperRegistry registry = new MapperRegistry();
//        registry.addMappers("com.nbsb.mybatis.test.dao");
////         2. 从 SqlSession 工厂获取 Session
//        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        // 3. 获取映射器对象
//        IHomeInfoDao dao = sqlSession.getMapper(IHomeInfoDao.class);
//        // 4. 进行调用方法
//        String s = dao.queryHomeName("1");
//        logger.info("res1: {}", s);
//
//        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
//        String res = mapper.queryUserName("1", "whn");
//        logger.info("res2: {}", res);
    }

    @Test
    public void test_class_agent_3() throws DocumentException, IOException {
        //这一步要解决读取xml中文件内容，把上一步配置的packageName放到xml中的namespace内容中，在使用dom4j进行解析xml文件
        //这一步主要目的：将mapper中代理的sql文本和参数、调用方法输出出来
        //实现流程：使用SqlSessionFactoryBuilder进行构建
        // 1. 将xml文件进行解析，解析出来映射到mapping->MappedStatement 中
        // 2. 将代理的sqlsession进行打印出相关信息，如将mapper中代理的sql文本和参数、调用方法输出出来
        // 3. 将MapperRegistry方法都放到MapperConfig类中进行实现

//        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
//        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
//        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        //测试查询user
//        IUserDao userMapper = sqlSession.getMapper(IUserDao.class);
//        String name = userMapper.queryUserInfoById("1");
//        System.out.println(name);
//        //测试查询home
//        IHomeInfoDao mapper = sqlSession.getMapper(IHomeInfoDao.class);
//        String s = mapper.queryHomeName("1");
//        System.out.println(s);
    }

    @Test
    public void test_class_agent_4() throws DocumentException, IOException {
        //① 上一步完成了Mapper_XML解析、SQL对象封装，并通过代理对象获取SQL语句并打印
        //② 就基于此完成SQL的真正执行，包括解析数据源配置、建立事务框架、引入DRUID连接池、创建和使用数据源
        // 主要内容：将上一步解析的sql进行执行，并且解析xml中的环境配置
        //   事务方面 在后面进行实现对事务的管理，这一步只搭建一个事务管理的框架
        //这里的数据源连接池我们采用的是阿里的 Druid
        // 1. 从SqlSessionFactory中获取SqlSession
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config-datasource.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        User user = userDao.queryUserInfoById(1L);
        logger.info("测试结果：{}", JSON.toJSONString(user));
    }

    @Test
    public void test_class_agent_5() throws DocumentException, IOException {
        //将数据库工厂增加使用数据库池化，通过连接池管理和复用数据库连接，从而提高性能和吞吐量
        //无池化的UnpooledDataSource每次都会创建新的数据库连接，不会对连接进行池化和复用，适用于小型应用或者对连接数要求不高的场景。
        //有池化的PooledDataSource通过连接池管理连接，可以对连接进行池化和复用，有效减少了连接的创建和销毁开销，适用于高并发或对连接数要求较高的场景。


    }}
