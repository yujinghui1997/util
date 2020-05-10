package com.yjh.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class MyDruidAutoConfiguration {

    @Autowired(required = false)
    private MyDruidProperties myDruidConfig;

   @Bean
    @ConditionalOnProperty(prefix = "com.yjh.mysql", value = "open", matchIfMissing = true)
    public  DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(myDruidConfig.getDriverClassName());
        ds.setUrl(myDruidConfig.getUrl());
        ds.setUsername(myDruidConfig.getUsername());
        ds.setPassword(myDruidConfig.getPassword());
        ds.setInitialSize(myDruidConfig.getInit());
        ds.setMinIdle(myDruidConfig.getMin());
        ds.setMaxActive(myDruidConfig.getMax());
        ds.setMaxWait(myDruidConfig.getMaxWait());
        ds.setTimeBetweenEvictionRunsMillis(myDruidConfig.getTimeBetweenEvictionRunSmillis());
        ds.setMinEvictableIdleTimeMillis(myDruidConfig.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(myDruidConfig.getValidationQuery());
        ds.setTestWhileIdle(myDruidConfig.isTestWhileIdle());
        ds.setTestOnBorrow(myDruidConfig.getTestOnBorrow());
        ds.setTestOnReturn(myDruidConfig.getTestOnReturn());
        ds.setPoolPreparedStatements(myDruidConfig.getPoolPreparedStatements());
        ds.setMaxOpenPreparedStatements(myDruidConfig.getMaxOpenPreparedStatements());
        ds.setConnectionProperties(myDruidConfig.getConnectionProperties());
        ds.setFilters(myDruidConfig.getFilters());
        return ds;
    }


}
