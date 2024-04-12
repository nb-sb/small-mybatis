package com.nbsb.mybatis.datasource.pooled;

import com.nbsb.mybatis.datasource.DataSourceException;
import com.nbsb.mybatis.datasource.unpooled.UnpooledDataSource;
import com.nbsb.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.nbsb.mybatis.reflection.MetaObject;
import com.nbsb.mybatis.reflection.SystemMetaObject;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 池化数据源工厂
 * @create: 2024/3/28 11:22
 */
public class PooledDataSourceFactory extends UnpooledDataSourceFactory {

    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }


}
