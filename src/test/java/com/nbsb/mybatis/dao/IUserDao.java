package com.nbsb.mybatis.dao;


public interface IUserDao {

    String queryUserName(String uId,String name);

    Integer queryUserAge(String uId,String name);

}
