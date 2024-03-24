package com.nbsb.mybatis.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
/**
* @author: Wanghaonan @戏人看戏
* @description: JDBC类型枚举
* @create: 2024/3/24 15:22
*/
public enum JdbcType {

    INTEGER(Types.INTEGER),
    FLOAT(Types.FLOAT),
    DOUBLE(Types.DOUBLE),
    DECIMAL(Types.DECIMAL),
    VARCHAR(Types.VARCHAR),
    TIMESTAMP(Types.TIMESTAMP);

    public final int TYPE_CODE;
    private static Map<Integer,JdbcType> codeLookup = new HashMap<>();

    // 就将数字对应的枚举型放入 HashMap
    static {
        for (JdbcType type : JdbcType.values()) {
            codeLookup.put(type.TYPE_CODE, type);
        }
    }

    JdbcType(int code) {
        this.TYPE_CODE = code;
    }
    public static JdbcType forType(String type) {
        return Enum.valueOf(JdbcType.class, type);
    }
    public int getTYPE_CODE() {
        return TYPE_CODE;
    }
    public static JdbcType forCode(int code)  {
        return codeLookup.get(code);
    }
}
