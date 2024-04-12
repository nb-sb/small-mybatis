package com.nbsb.mybatis.test.stringtest;

import org.junit.Test;

public class test {

    @Test
    public void test(){
        String str = "abc";
        System.out.println(str.substring(0,3));
        String a = "111aabbcc[";
        System.out.println(a.length());
        System.out.println(a.indexOf("c"));
    }

}
