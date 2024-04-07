package com.nbsb.mybatis.scripting;

import com.nbsb.mybatis.mapping.SqlSource;
import com.nbsb.mybatis.session.Configuration;
import org.dom4j.Element;

public interface LanguageDriver {

    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);
}
