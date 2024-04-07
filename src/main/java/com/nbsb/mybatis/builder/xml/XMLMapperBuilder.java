package com.nbsb.mybatis.builder.xml;

import com.nbsb.mybatis.builder.BaseBuilder;
import com.nbsb.mybatis.io.Resources;
import com.nbsb.mybatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 用户解析mapper中的元素
 * @create: 2024/4/7 09:49
 */
public class XMLMapperBuilder extends BaseBuilder{
    private Element element;
    private String resource;
    private String currentNamespace;

    public XMLMapperBuilder(InputStream inputStream, Configuration configuration, String resource) throws DocumentException {
        this(new SAXReader().read(inputStream), configuration, resource);
    }

    private XMLMapperBuilder(Document document, Configuration configuration, String resource) {
        super(configuration);
        this.element = document.getRootElement();
        this.resource = resource;
    }

    public void parse() throws ClassNotFoundException {
        //判断资源是否已经加载了，防止重复加载
        if (!configuration.isResourceLoaded(resource)) {
            configurationElement(element);
            configuration.addLoadedResource(resource);
            //绑定到映射器注册机
            Class<?> type = Resources.classForName(currentNamespace); //将这个包名称加载成类
            configuration.getMapperRegistry().addMapper(type);
        }
    }

    /**
     * <mapper namespace="com.nbsb.mybatis.test.dao.IUserDao">
     * <select id="queryUserInfoById" parameterType="java.lang.Long" resultType="com.nbsb.mybatis.test.po.User">
     * SELECT id,name,age
     * FROM user
     * where id = #{id}
     * </select>
     * </mapper>
     *
     * @param element
     */
    private void configurationElement(Element element) {
        // 1.配置namespace
        currentNamespace = element.attributeValue("namespace");
        if (currentNamespace.equals("")) {
            throw new RuntimeException("Mapper's namespace cannot be empty");
        }
        // 2.配置select|insert|update|delete
        buildStatementFromContext(element.elements("select"));
    }
    // 配置select|insert|update|delete
    private void buildStatementFromContext(List<Element> list) {
        for (Element element : list) {
            final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, element, currentNamespace);
            statementParser.parseStatementNode();
        }
    }
}
