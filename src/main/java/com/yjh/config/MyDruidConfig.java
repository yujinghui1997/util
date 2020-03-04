package com.yjh.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.filter.Filter;

import java.util.ArrayList;
import java.util.List;

public class MyDruidConfig {
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://{}?characterEncoding=utf-8&serverTimezone=GMT%2B8&allowMultiQueries=true";
    private String username = "root";
    private String password ="root";
    private Integer init = 5;
    private Integer min = 5;
    private Integer max = 20;
    private Long maxWait = 60000L;
    private Long timeBetweenEvictionRunSmillis = 60000L;
    private Long minEvictableIdleTimeMillis = 30000L;
    private String validationQuery = "select 'x'";
    private boolean testWhileIdle = true;
    private Boolean testOnBorrow = true;
    private Boolean testOnReturn = false;
    private Boolean poolPreparedStatements = true;
    private Integer maxOpenPreparedStatements = 20;
    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500";
    private String filters = "stat";
    private List<Filter> proxyFilters = new ArrayList<>();

    public MyDruidConfig(){}

    public Integer getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(Integer maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }

    public String getDriverClassName() {
        return driverClassName;
    }
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = StrUtil.format(this.url,url);
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getInit() {
        return init;
    }
    public void setInit(Integer init) {
        this.init = init;
    }
    public Integer getMin() {
        return min;
    }
    public void setMin(Integer min) {
        this.min = min;
    }
    public Integer getMax() {
        return max;
    }
    public void setMax(Integer max) {
        this.max = max;
    }
    public Long getMaxWait() {
        return maxWait;
    }
    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }
    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }
    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }
    public Long getTimeBetweenEvictionRunSmillis() {
        return timeBetweenEvictionRunSmillis;
    }
    public void setTimeBetweenEvictionRunSmillis(Long timeBetweenEvictionRunSmillis) {
        this.timeBetweenEvictionRunSmillis = timeBetweenEvictionRunSmillis;
    }
    public Long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }
    public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }
    public String getValidationQuery() {
        return validationQuery;
    }
    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }
    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }
    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
    public Boolean getTestOnReturn() {
        return testOnReturn;
    }
    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }
    public Boolean getPoolPreparedStatements() {
        return poolPreparedStatements;
    }
    public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }
    public String getConnectionProperties() {
        return connectionProperties;
    }
    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
    public String getFilters() {
        return filters;
    }
    public void setFilters(String filters) {
        this.filters = filters;
    }
    public List<Filter> getProxyFilters() {
        return proxyFilters;
    }
    public void setProxyFilters(List<Filter> proxyFilters) {
        this.proxyFilters = proxyFilters;
    }
}
