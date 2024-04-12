package com.nbsb.mybatis.datasource.unpooled;

import com.nbsb.mybatis.datasource.DataSourceException;
import com.nbsb.mybatis.datasource.DataSourceFactory;
import com.nbsb.mybatis.reflection.MetaObject;
import com.nbsb.mybatis.reflection.SystemMetaObject;

import javax.sql.DataSource;
import java.util.Properties;

public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected DataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Properties props) {
//        UnpooledDataSource datasource1 = new UnpooledDataSource();
//        datasource1.setDriver(props.getProperty("driver"));
//        datasource1.setUrl(props.getProperty("url"));
//        datasource1.setUsername(props.getProperty("username"));
//        datasource1.setPassword(props.getProperty("password"));
//        this.dataSource = datasource1;
        MetaObject metaObject = SystemMetaObject.forObject(dataSource);
        //将所有的key都给循环出来
        for (Object key : props.keySet()) {
            String propertyName = (String) key;
            //如果存在的话则进行设置
            if (metaObject.hasSetter(propertyName)) {
                //获取到这个key对应的内容
                String value = (String) props.get(propertyName);
                //转为相应的类型的参数，然后进行设置
                Object convertedValue = convertValue(metaObject, propertyName, value);
                //这里使用反射直接设置dataSource的值
                metaObject.setValue(propertyName, convertedValue);
            } else {
                //不支持的类型，也就是说在UnpooledDataSource中没有这个参数
                throw new DataSourceException("Unknown DataSource property: " + propertyName);
            }
        }
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
    /**
     * 根据setter的类型,将配置文件中的值强转成相应的类型
     */
    private Object convertValue(MetaObject metaObject, String propertyName, String value) {
        Object convertedValue = value;
        Class<?> targetType = metaObject.getSetterType(propertyName);
        if (targetType == Integer.class || targetType == int.class) {
            convertedValue = Integer.valueOf(value);
        } else if (targetType == Long.class || targetType == long.class) {
            convertedValue = Long.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            convertedValue = Boolean.valueOf(value);
        }
        return convertedValue;
    }
}
