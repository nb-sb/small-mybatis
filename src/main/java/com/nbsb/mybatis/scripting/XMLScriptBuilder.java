package com.nbsb.mybatis.scripting;

import com.nbsb.mybatis.builder.BaseBuilder;
import com.nbsb.mybatis.mapping.SqlSource;
import com.nbsb.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: XML脚本构建器
 * @create: 2024/4/7 11:02
 */
public class XMLScriptBuilder extends BaseBuilder {
    private Element element;
    private boolean isDynamic;
    private Class<?> parameterType;

    public XMLScriptBuilder(Configuration configuration, Element element, Class<?> parameterType) {
        super(configuration);
        this.element = element;
        this.parameterType = parameterType;
    }

    public SqlSource parseScriptNode() {
        return null;
    }

//    public SqlSource parseScriptNode() {
//        List<SqlNode> contents = parseDynamicTags(element);
//        MixedSqlNode rootSqlNode = new MixedSqlNode(contents);
//        return new RawSqlSource(configuration, rootSqlNode, parameterType);
//    }
//
//    List<SqlNode> parseDynamicTags(Element element) {
//        List<SqlNode> contents = new ArrayList<>();
//        // element.getText 拿到 SQL
//        String data = element.getText();
//        contents.add(new StaticTextSqlNode(data));
//        return contents;
//    }
}
