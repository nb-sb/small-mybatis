package com.nbsb.mybatis.test.enumtest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class invokeMethod<T> {

    public <T> T invoke(T tclass, String a) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method;
        method = tclass.getClass().getMethod("setName", String.class);
        method.invoke(tclass, a);
        return tclass;
    }
}
