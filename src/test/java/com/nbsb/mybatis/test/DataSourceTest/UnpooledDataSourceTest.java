package com.nbsb.mybatis.test.DataSourceTest;

import com.nbsb.mybatis.datasource.unpooled.UnpooledDataSource;
import com.nbsb.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class UnpooledDataSourceTest {
    //测试驱动代理时候可以正常运行
    @Test
    public void test() throws SQLException {
        UnpooledDataSource unpooledDataSource = new UnpooledDataSource(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://127.0.0.1:3306/mybatis",
                new Properties()
        );
        Map<String, Driver> registeredDrivers = unpooledDataSource.getRegisteredDrivers();
        Driver driver = registeredDrivers.get("com.mysql.jdbc.Driver");
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "123456");
        Connection connect = driver.connect(unpooledDataSource.getUrl(), props);

        DruidDataSourceTest.extracted(connect);
    }

    @Test
    public void testUnpooled() throws SQLException {
        Properties props = new Properties();
        props.setProperty("driver","com.mysql.jdbc.Driver");
        props.setProperty("url","jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true");
        props.setProperty("username","root");
        props.setProperty("password","123456");
        UnpooledDataSourceFactory unpooledDataSourceFactory = new UnpooledDataSourceFactory();
        unpooledDataSourceFactory.setProperties(props);
        DataSource dataSource = unpooledDataSourceFactory.getDataSource();

        Connection connection = dataSource.getConnection();

        DruidDataSourceTest.extracted(connection);
    }

}
