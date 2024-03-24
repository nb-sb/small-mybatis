package com.nbsb.mybatis.test.enumtest;

import com.nbsb.mybatis.type.JdbcType;
import org.junit.Test;

import java.util.Properties;

public class TestEnum {

    @Test
    public void testEnum() {
//        System.out.println(TransactionIsolationLevel.NONE.getLevel());
        System.out.println(JdbcType.VARCHAR.getTYPE_CODE());
        System.out.println(JdbcType.forCode(JdbcType.VARCHAR.getTYPE_CODE()));
    }
    @Test
    public void testProperties() {
        Properties props = new Properties();
        props.setProperty("123", "oknb");
        props.setProperty("456", "oknb");
        System.out.println(props.get("123"));
    }

}
