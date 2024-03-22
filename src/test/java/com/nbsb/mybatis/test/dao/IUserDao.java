package com.nbsb.mybatis.test.dao;


public interface IUserDao {
    String queryUserInfoById(String id);
    String queryUserName(String uId,String name);

    Integer queryUserAge(String uId,String name);

}
