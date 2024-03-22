package com.nbsb.mybatis.session;

import com.nbsb.mybatis.builder.xml.XMLConfigBuilder;
import com.nbsb.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 用户构建SqlSessionFactory
 * @create: 2024/3/22 13:18
 */
public class SqlSessionFactoryBuilder {
    //使用configuration类进行创建SqlSessionFactory
    public SqlSessionFactory build(Configuration configuration) {

        return new DefaultSqlSessionFactory(configuration);
    }

    //使用xml的reader将其解析为config然后在生成SqlSessionFactory
    public SqlSessionFactory build(Reader reader) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        Configuration parse = xmlConfigBuilder.parse();
        return build(parse);

    }
}
