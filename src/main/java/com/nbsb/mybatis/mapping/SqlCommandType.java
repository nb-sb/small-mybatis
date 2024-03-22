package com.nbsb.mybatis.mapping;

/**
* @author: Wanghaonan @戏人看戏
* @description: SQL 指令类型
* @create: 2024/3/22 13:05
*/
public enum SqlCommandType {

    /**
     * 未知
     */
    UNKNOWN,
    /**
     * 插入
     */
    INSERT,
    /**
     * 更新
     */
    UPDATE,
    /**
     * 删除
     */
    DELETE,
    /**
     * 查找
     */
    SELECT;

}
