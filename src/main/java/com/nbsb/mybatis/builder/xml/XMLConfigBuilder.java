package com.nbsb.mybatis.builder.xml;

import com.nbsb.mybatis.binding.MapperProxy;
import com.nbsb.mybatis.builder.BaseBuilder;
import com.nbsb.mybatis.datasource.DataSourceFactory;
import com.nbsb.mybatis.io.Resources;
import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.Environment;
import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.mapping.SqlCommandType;
import com.nbsb.mybatis.session.Configuration;
import com.nbsb.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.nbsb.mybatis.type.TypeAliasRegistry;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: XML配置构建器，建造者模式，继承BaseBuilder
 * @create: 2024/3/22 13:12
 */
public class XMLConfigBuilder extends BaseBuilder {
    private Element root;//根节点信息,在Builder的时候存贮着根节点信息，等到parse成config类的时候在使用
    private Logger logger = LoggerFactory.getLogger(XMLConfigBuilder.class);
    /**
     * @Des 传入对应配置文件路径地址的reader类型
     * @Date 2024/3/22 13:12
     */
    public XMLConfigBuilder(Reader reader) {
        // 1. 调用父类初始化Configuration
        super(new Configuration());
        // 2. dom4j 处理 xml
        SAXReader saxReader = new SAXReader();
        try {
            //使用dom4j进行获取跟节点信息
            Document document = saxReader.read(new InputSource(reader));
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Des 将其root根节点中数据进行解析
     * 解析配置；类型别名、插件、对象工厂、对象包装工厂、设置、环境、类型转换、映射器
     * @Date 2024/3/22 13:22
     * @Return Configuration
     */
    public Configuration parse() {
        try {
            System.out.println("root name: "+root.getName());
            //解析mysql运行环境
            environmentsElement(root.element("environments"));
            // 解析映射器
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }

    /**
     *     <!--mysql数据库环境配置-->
     *     <environments default="development">
     *         <environment id="development">
     *             <transactionManager type="JDBC"/>
     *             <dataSource type="DRUID">
     *                 <property name="driver" value="com.mysql.jdbc.Driver"/>
     *                 <property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true"/>
     *                 <property name="username" value="root"/>
     *                 <property name="password" value="123456"/>
     *             </dataSource>
     *         </environment>
     *     </environments>
     * @param mappers
     * @throws Exception
     */
    private void environmentsElement(Element mappers)throws Exception {
        String environment = mappers.attributeValue("default"); //获取使用配置的环境标识符
        List<Element> mapperList = mappers.elements("environment");
        for (Element element : mapperList) {
            //这里可能会有很多配置，例如dev、test、pro等环境配置，要获取当前使用的
            String currentEnvironment = element.attributeValue("id");
            if (!currentEnvironment.equals(environment)) {
                //如果不是需要的配置的话就跳过，查询下一个
                continue;
            }
            //1.创建事务管理器
            //获取类型 <transactionManager type="JDBC"/>
            Element transactionManager = element.element("transactionManager");
            String type = transactionManager.attributeValue("type");
            JdbcTransactionFactory jdbcTransactionFactory = (JdbcTransactionFactory) typeAliasRegistry.resolveAlias(type).newInstance();
            //2.获取数据源
            Element dataSourceElement = element.element("dataSource");
            String dataSourceType = dataSourceElement.attributeValue("type");
            logger.info("使用的数据源类型:{} ",dataSourceType);
            DataSourceFactory dataSourceFactory = (DataSourceFactory) typeAliasRegistry.resolveAlias(dataSourceType).newInstance();
            List<Element> propertyList = dataSourceElement.elements("property");
            //创建属性值，类似于一个map的对象
            Properties props = new Properties();
            for (Element property : propertyList) {
                props.setProperty(property.attributeValue("name"), property.attributeValue("value"));
            }
            dataSourceFactory.setProperties(props);
            DataSource dataSource = dataSourceFactory.getDataSource();
            //3.builder构建环境
            Environment env = new Environment.Builder(currentEnvironment)
                    .transactionFactory(jdbcTransactionFactory)
                    .dataSource(dataSource).build();
            configuration.setEnvironment(env);

        }

    }

    /**
     * <mappers>
     *         <mapper resource="mapper/User_Mapper.xml"/>
     *         <mapper resource="mapper/Home_Mapper.xml"/>
     * </mappers>
     * @param mappers
     * @throws Exception
     */
    private void mapperElement(Element mappers) throws Exception {
        System.out.println(mappers.getName());
        List<Element> mapperList = mappers.elements("mapper");
        for (Element e : mapperList) {
            String resource = e.attributeValue("resource");
            Reader reader = Resources.getResourceAsReader(resource);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();
            //命名空间
            String namespace = root.attributeValue("namespace");

            // SELECT
            List<Element> selectNodes = root.elements("select");
            for (Element node : selectNodes) {
                /**
                 * <select id="queryUserInfoById" parameterType="java.lang.Long" resultType="com.nbsb.mybatis.test.po.User">
                 *         SELECT id,name,age
                 *         FROM user
                 *         where id = #{id}
                 * </select>
                 */
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                // ? 匹配
                Map<Integer, String> parameter = new HashMap<>();
                Pattern pattern = Pattern.compile("(#\\{(.*?)})"); //获取#{}中的内容
                Matcher matcher = pattern.matcher(sql);
                for (int i = 1; matcher.find(); i++) {
                    String g1 = matcher.group(1);//#{name}
                    String g2 = matcher.group(2);// whn
                    parameter.put(i, g2);//获取到参数，例如第一个参数中name填whn
                    sql = sql.replace(g1, "?");
                }

                String msId = namespace + "." + id;//方法的类，如：com.nbsb.mybatis.test.dao.IUserDao.queryUserInfoById
                String nodeName = node.getName();
                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
                BoundSql boundSql = new BoundSql(sql, parameter, parameterType, resultType);
                MappedStatement mappedStatement = new MappedStatement.Builder( configuration,msId, sqlCommandType, boundSql).build();
                // 添加解析 SQL
                configuration.getMappedStatements().put(mappedStatement.getId(), mappedStatement);
            }

            // 注册Mapper映射器
            Class<?> type = Resources.classForName(namespace); //将这个包名称加载成类
            configuration.getMapperRegistry().addMapper(type);
        }
    }
}
