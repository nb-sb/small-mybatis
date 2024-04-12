package com.nbsb.mybatis.datasource;
/**
* @author: Wanghaonan @戏人看戏
* @description: DataSourceException用于抛出数据资源中的错误提示
* @create: 2024/4/8 11:15
*/
public class DataSourceException extends RuntimeException {
    private static final long serialVersionUID = -5251396250407091334L;

    public DataSourceException() {
    }
    public DataSourceException(String message) {
        super(message);
    }
    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
