package com.nbsb.mybatis.scripting;

import com.nbsb.mybatis.mapping.SqlSource;
import com.nbsb.mybatis.session.Configuration;
import org.dom4j.Element;

/**
* @author: Wanghaonan @戏人看戏
* @description: XML语言驱动器
* @create: 2024/4/7 10:50
*/
public class XMLLanguageDriver implements LanguageDriver{
    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }
}
