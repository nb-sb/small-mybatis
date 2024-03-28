package com.nbsb.mybatis.test.DataSourceTest;

import com.nbsb.mybatis.datasource.pooled.PooledDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PooledDataSourceTest {
    @Test
    public void pooledDataSourceTest() throws SQLException, InterruptedException {
        Properties props = new Properties();
        props.setProperty("driver","com.mysql.jdbc.Driver");
        props.setProperty("url","jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true");
        props.setProperty("username","root");
        props.setProperty("password","123456");
        PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
        pooledDataSourceFactory.setProperties(props);
        DataSource dataSource = pooledDataSourceFactory.getDataSource();
//        Connection connection = dataSource.getConnection();
//        DruidDataSourceTest.extracted(connection);//执行sql测试
        //测试连接池中连接
        while (true) {
            Connection connection = dataSource.getConnection();
            System.out.println(connection);
            Thread.sleep(1000);
        }
    }
}
