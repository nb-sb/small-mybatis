package com.nbsb.mybatis.scripting;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: SQL 节点
 * @create: 2024/4/7 11:03
 */
public interface SqlNode {
    boolean apply(DynamicContext context);
}
