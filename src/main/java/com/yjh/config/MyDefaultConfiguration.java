package com.yjh.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.yjh.comp.MyAuthInterceptor;
import com.yjh.comp.MyCrosFilter;
import com.yjh.properties.MyCrosFilterProperties;
import com.yjh.properties.MyDruidProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
@EnableAutoConfiguration(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class})
public class MyDefaultConfiguration implements WebMvcConfigurer {

    @Autowired(required = false)
    private MyDruidProperties myDruidProperties;
    @Autowired(required = false)
    private MyAuthInterceptor myAuthInterceptor;
    @Autowired(required = false)
    private MyCrosFilterProperties myCrosFilterProperties;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myAuthInterceptor).addPathPatterns("/**").order(0);
    }

    /**
     * 跨域配置
     * @return
     */
    @Conditional(MyCrosFilterProperties.class)
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        Filter filter = new MyCrosFilter(myCrosFilterProperties.getOrigin(),myCrosFilterProperties.getHeaders());
        filterRegistration.setFilter(filter);
        filterRegistration.setName("corsFilter");
        filterRegistration.setUrlPatterns(Arrays.asList("*"));
        filterRegistration.setOrder(myCrosFilterProperties.getOrder());
        return filterRegistration;
    }

    /**
     * mysql配置
     * @return
     * @throws SQLException
     */
    @Conditional(MyDruidProperties.class)
    @Bean
    public DruidDataSource druidDataSource() throws SQLException {
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(myDruidProperties.getDriverClassName());
        ds.setUrl(myDruidProperties.getUrl());
        ds.setUsername(myDruidProperties.getUsername());
        ds.setPassword(myDruidProperties.getPassword());
        ds.setInitialSize(myDruidProperties.getInit());
        ds.setMinIdle(myDruidProperties.getMin());
        ds.setMaxActive(myDruidProperties.getMax());
        ds.setMaxWait(myDruidProperties.getMaxWait());
        ds.setTimeBetweenEvictionRunsMillis(myDruidProperties.getTimeBetweenEvictionRunSmillis());
        ds.setMinEvictableIdleTimeMillis(myDruidProperties.getMinEvictableIdleTimeMillis());
        ds.setValidationQuery(myDruidProperties.getValidationQuery());
        ds.setTestWhileIdle(myDruidProperties.isTestWhileIdle());
        ds.setTestOnBorrow(myDruidProperties.getTestOnBorrow());
        ds.setTestOnReturn(myDruidProperties.getTestOnReturn());
        ds.setPoolPreparedStatements(myDruidProperties.getPoolPreparedStatements());
        ds.setMaxOpenPreparedStatements(myDruidProperties.getMaxOpenPreparedStatements());
        ds.setConnectionProperties(myDruidProperties.getConnectionProperties());
        ds.setFilters(myDruidProperties.getFilters());
        return ds;
    }



}
