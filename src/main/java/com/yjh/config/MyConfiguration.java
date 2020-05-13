package com.yjh.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MyConfiguration {

    @Autowired(required = false)
    private MyDruidProperties mdp;
    @Autowired(required = false)
    private MyCrosFilterProperties mcfp;

    @Bean
    @ConditionalOnProperty(prefix = "com.yjh.mysql", value = "open", matchIfMissing = true)
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(mdp.getDriverClassName());
        ds.setUrl(mdp.getUrl());
        ds.setUsername(mdp.getUsername());
        ds.setPassword(mdp.getPassword());
        ds.setInitialSize(mdp.getInit());
        ds.setMinIdle(mdp.getMin());
        ds.setMaxActive(mdp.getMax());
        ds.setMaxWait(mdp.getMaxWait());
        ds.setTimeBetweenEvictionRunsMillis(mdp.getTimeBetweenEvictionRunSmillis());
        ds.setMinEvictableIdleTimeMillis(mdp.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(mdp.getValidationQuery());
        ds.setTestWhileIdle(mdp.isTestWhileIdle());
        ds.setTestOnBorrow(mdp.getTestOnBorrow());
        ds.setTestOnReturn(mdp.getTestOnReturn());
        ds.setPoolPreparedStatements(mdp.getPoolPreparedStatements());
        ds.setMaxOpenPreparedStatements(mdp.getMaxOpenPreparedStatements());
        ds.setConnectionProperties(mdp.getConnectionProperties());
        ds.setFilters(mdp.getFilters());
        return ds;
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        String  whiteList =  mcfp.getWhiteList();
        if (!"*".equals(whiteList)){
            String[] strArr =  whiteList.split(",");
            List<String> strList = Arrays.asList(strArr);
           whiteList =  strList.stream().map(s -> {
                return "http://" + s;
            }).collect(Collectors.joining(","));
        }
        Filter filter =  new MyCrosFilter(mcfp.getOpen(),whiteList);
        filterRegistration.setFilter(filter);
        filterRegistration.setName(mcfp.getName());
        String[] urls = mcfp.getUrlPattern().split(",");
        List<String> urlPattern = Arrays.asList(urls);
        filterRegistration.setUrlPatterns(urlPattern);
        filterRegistration.setOrder(mcfp.getOrder());
        return filterRegistration;
    }


}
