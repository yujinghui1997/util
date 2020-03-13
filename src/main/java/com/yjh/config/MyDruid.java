package com.yjh.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyDruid {

    public static DruidDataSource create(MyDruidConfig myDruidConfig) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(myDruidConfig.getDriverClassName());
        dataSource.setUrl(myDruidConfig.getUrl());
        dataSource.setUsername(myDruidConfig.getUsername());
        dataSource.setPassword(myDruidConfig.getPassword());
        dataSource.setInitialSize(myDruidConfig.getInit());
        dataSource.setMinIdle(myDruidConfig.getMin());
        dataSource.setMaxActive(myDruidConfig.getMax());
        dataSource.setMaxWait(myDruidConfig.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(myDruidConfig.getTimeBetweenEvictionRunSmillis());
        dataSource.setMinEvictableIdleTimeMillis(myDruidConfig.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(myDruidConfig.getValidationQuery());
        dataSource.setTestWhileIdle(myDruidConfig.isTestWhileIdle());
        dataSource.setTestOnBorrow(myDruidConfig.getTestOnBorrow());
        dataSource.setTestOnReturn(myDruidConfig.getTestOnReturn());
        dataSource.setPoolPreparedStatements(myDruidConfig.getPoolPreparedStatements());
        dataSource.setMaxOpenPreparedStatements(myDruidConfig.getMaxOpenPreparedStatements());
        dataSource.setConnectionProperties(myDruidConfig.getConnectionProperties());
        dataSource.setFilters(myDruidConfig.getFilters());
        dataSource.setProxyFilters(myDruidConfig.getProxyFilters());
        return dataSource;
    }

    public static List<Filter> getFilter() {
        List<Filter> list = new ArrayList<Filter>();
        WallFilter wallFilter = new WallFilter();
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);
        wallConfig.setNoneBaseStatementAllow(true);
        wallFilter.setConfig(wallConfig);
        list.add(wallFilter);
        return list;
    }
}
