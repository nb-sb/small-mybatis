package com.nbsb.mybatis.test.DataSourceTest;

import com.alibaba.druid.pool.DruidDataSource;
import com.nbsb.mybatis.datasource.druid.DruidDataSourceFactory ;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.nbsb.mybatis.executor.resultset.DefaultResultSetHandler;
import com.nbsb.mybatis.mapping.BoundSql;
import org.junit.Test;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.DuplicateFormatFlagsException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class DruidDataSourceTest {

    @Test
    public void dataDruidjdbctest() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        DruidPooledConnection connection = dataSource.getConnection();
        extracted2(connection);

    }


    static void extracted(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,name,age\n" +
                "        FROM user\n" +
                "        where id = ?");
        preparedStatement.setLong(1, 1);
        ResultSet resultSet = preparedStatement.executeQuery();
        int columnCount = resultSet.getMetaData().getColumnCount();
        System.out.println(columnCount);
        while (resultSet.next()) {
            System.out.println(resultSet.getLong(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3));
        }
    }

    static void extracted2(Connection connection) throws SQLException {
        Statement statement = connection.prepareStatement("SELECT id,name,age\n" +
                "        FROM user\n" +
                "        where id = ?");
        statement.setQueryTimeout(350);
        statement.setFetchSize(10000);
        PreparedStatement ps = (PreparedStatement) statement;
        ps.setLong(1, 1);
        boolean execute = ps.execute();
        System.out.println(execute);
        BoundSql boundSql = new BoundSql(null, null, null, "com.nbsb.mybatis.test.po.User");
        DefaultResultSetHandler defaultResultSetHandler = new DefaultResultSetHandler(boundSql);
        List<Object> objects = defaultResultSetHandler.handleResultSets(ps);
        System.out.println(objects);
    }

    @Test
    public void dataDruidFactoryTest() throws SQLException {

        Properties props = new Properties();
        props.setProperty("driver","com.mysql.jdbc.Driver");
        props.setProperty("url","jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true");
        props.setProperty("username","root");
        props.setProperty("password","123456");
        DruidDataSourceFactory druidDataSourceFactory = new DruidDataSourceFactory();
        druidDataSourceFactory.setProperties(props);
        DataSource dataSource = druidDataSourceFactory.getDataSource();
        //获取到连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    /**
     * 在运行时选择JDBC驱动程序
     */
    @Test
    public void will_work() throws SQLException, ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
        URL u = new URL("jar:file:/path/to/pgjdbc2.jar!/");
        String classname = "com.mysql.jdbc.Driver";
        URLClassLoader ucl = new URLClassLoader(new URL[] { u });
        Driver d = (Driver) Class.forName(classname, true, ucl).newInstance();
        DriverManager.registerDriver(new DriverShim(d));
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            System.out.println(driver.getClass().getName());
        }
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true", "root", "123456");
        System.out.println(connection);
        extracted(connection);
    }
}
