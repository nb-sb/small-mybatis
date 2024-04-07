package com.nbsb.mybatis.builder.xml;

import com.nbsb.mybatis.builder.BaseBuilder;
import com.nbsb.mybatis.builder.StaticSqlSource;
import com.nbsb.mybatis.mapping.BoundSql;
import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.mapping.SqlCommandType;
import com.nbsb.mybatis.mapping.SqlSource;
import com.nbsb.mybatis.session.Configuration;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 用户解析mapper元素中的具体的语句，select、update、insert、delete等
 * @create: 2024/4/7 09:51
 */
public class XMLStatementBuilder extends BaseBuilder {
    private String currentNamespace;
    private Element element;

    public XMLStatementBuilder(Configuration configuration, Element element, String currentNamespace) {
        super(configuration);
        this.element = element;
        this.currentNamespace = currentNamespace;
    }
    /**
     * <select id="queryUserInfoById" parameterType="java.lang.Long" resultType="com.nbsb.mybatis.test.po.User">
     *         SELECT id,name,age
     *         FROM user
     *         where id = #{id}
     * </select>
     */
    public void parseStatementNode() {
        String id = element.attributeValue("id");
        // 参数类型
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveAlias(parameterType);
        // 结果类型
        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveAlias(resultType);
        // 获取命令类型(select|insert|update|delete)
        String nodeName = element.getName();
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

        // 获取默认语言驱动器
//        Class<?> langClass = configuration.getLanguageRegistry().getDefaultDriverClass();
//        LanguageDriver langDriver = configuration.getLanguageRegistry().getDriver(langClass);
//
//        SqlSource sqlSource = langDriver.createSqlSource(configuration, element, parameterTypeClass);
//
//        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, currentNamespace + "." + id, sqlCommandType, sqlSource, resultTypeClass).build();
//
//        // 添加解析 SQL
//        configuration.addMappedStatement(mappedStatement);

        String sql = element.getText();
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
        String msId = currentNamespace + "." + id;//方法的类，如：com.nbsb.mybatis.test.dao.IUserDao.queryUserInfoById
        BoundSql boundSql = new BoundSql(sql, parameter, parameterType, resultType);
        SqlSource sqlSource = new StaticSqlSource(configuration,sql);
        MappedStatement mappedStatement = new MappedStatement.Builder(configuration, msId, sqlCommandType, sqlSource,resultTypeClass).build();
        // 添加解析 SQL
        mappedStatement.setBoundSql(boundSql);
        configuration.getMappedStatements().put(mappedStatement.getId(), mappedStatement);
    }
}
