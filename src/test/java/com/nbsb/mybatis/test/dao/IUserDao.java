package com.nbsb.mybatis.test.dao;


import com.nbsb.mybatis.test.po.User;

public interface IUserDao {
    User queryUserInfoById(Long id);
    String queryUserName(String uId,String name);

    Integer queryUserAge(String uId,String name);

}
