package com.nbsb.mybatis.datasource.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.nbsb.mybatis.datasource.DataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 这里是使用德鲁伊的数据源，还可以配置其他数据源
 * @create: 2024/3/24 15:35
 */
public class DruidDataSourceFactory implements DataSourceFactory {

    private Properties props;

    @Override
    public void setProperties(Properties props) {
        this.props = props;
    }

    @Override
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(props.getProperty("driver"));
        dataSource.setUrl(props.getProperty("url"));
        dataSource.setUsername(props.getProperty("username"));
        dataSource.setPassword(props.getProperty("password"));
        return dataSource;
    }
}
