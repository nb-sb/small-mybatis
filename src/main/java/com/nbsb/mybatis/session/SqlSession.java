package com.nbsb.mybatis.session;
/**
* @author: Wanghaonan @戏人看戏
* @description: SqlSession 用来执行SQL，获取映射器，管理事务。
* @create: 2024/3/21 16:49
*/
public interface SqlSession {
    /**
     * 根据指定的SqlID获取一条记录的封装对象
     * @param statement sqlID
     * @param <T> 封装之后的对象类型
     * @return Mapped object 封装之后的对象
     */
    <T> T selectOne(String statement);

    /**
     * 检索从语句键和参数映射的单行
     * 根据指定的SqlID获取一条记录的封装对象，只不过这个方法容许我们可以给sql传递一些参数
     * 一般在实际使用中，这个参数传递的是pojo，或者Map或者ImmutableMap
     *
     * @param <T>       返回的对象类型
     * @param statement 与要使用的语句匹配的唯一标识符。
     * @param parameter 传递给语句的参数对象。
     * @return Mapped object
     */
    <T> T selectOne(String statement, Object parameter);
    /**
     * 获取mapper映射器
     * 得到映射器，这个巧妙的使用了泛型，使得类型安全
     *
     * @param <T>  映射器类型
     * @param type 映射器接口类
     * @return 绑定到这个SqlSession的映射器
     */
    <T> T getMapper(Class<T> type);
}
