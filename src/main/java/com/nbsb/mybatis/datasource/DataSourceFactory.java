package com.nbsb.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 数据源工厂
 * @create: 2024/3/24 15:32
 */
public interface DataSourceFactory {

    void setProperties(Properties props);

    DataSource getDataSource();
}
