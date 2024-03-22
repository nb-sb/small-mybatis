package com.nbsb.mybatis.binding;

import com.nbsb.mybatis.mapping.MappedStatement;
import com.nbsb.mybatis.mapping.SqlCommandType;
import com.nbsb.mybatis.session.Configuration;
import com.nbsb.mybatis.session.SqlSession;

import java.lang.reflect.Method;

/**
* @author: Wanghaonan @戏人看戏
* @description: sql的方法执行类
* @create: 2024/3/22 14:03
*/
public class MapperMethod {
    private final SqlCommand command;

    public MapperMethod(Configuration configuration, Class<?> mapperInterface, Method method) {
        this.command = new SqlCommand(configuration, mapperInterface, method);
    }
    /**
     * @Des 执行命令
     * @Date 2024/3/22 14:07
     * @Param sqlSession args参数
     * @Return Object
     */
    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result = null;
        switch (command.getType()) {
            case INSERT:
                break;
            case DELETE:
                break;
            case UPDATE:
                break;
            case SELECT:
                result = sqlSession.selectOne(command.getName(), args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command.getName());
        }
        return result;
    }

    /**
     * SQL 指令
     */
    public static class SqlCommand {

        private final String name;
        private final SqlCommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            String statementName = mapperInterface.getName() + "." + method.getName();
            MappedStatement ms = configuration.getMappedStatements().get(statementName);
            name = ms.getId(); // = statementName
            type = ms.getSqlCommandType();
        }

        public String getName() {
            return name;
        }

        public SqlCommandType getType() {
            return type;
        }
    }
}
