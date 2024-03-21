package com.nbsb.mybatis.session;
/**
* @author: Wanghaonan @戏人看戏
* @description: 工厂模式接口，构建SqlSession的工厂
* @create: 2024/3/21 21:53
*/
public interface SqlSessionFactory {
    /**
     * 打开一个 session
     * @return SqlSession
     */
    SqlSession openSession();
}
