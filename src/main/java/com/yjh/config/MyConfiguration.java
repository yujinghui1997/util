package com.yjh.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.yjh.comp.MyAuthInterceptor;
import com.yjh.comp.MyCrosFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.sql.SQLException;
import java.util.Arrays;

@Configuration
public class MyConfiguration implements WebMvcConfigurer {

    @Autowired(required = false)
    private MyDruidProperties mdp;
    @Autowired(required = false)
    private MyAuthInterceptor myAuthInterceptor;

    /**
     * 天机拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myAuthInterceptor).addPathPatterns("/**");
    }

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
        Filter filter =  new MyCrosFilter(mcfp.getOpen());
        filterRegistration.setFilter(filter);
        filterRegistration.setName("corsFilter");
        filterRegistration.setUrlPatterns(Arrays.asList("*"));
        filterRegistration.setOrder(mcfp.getOrder());
        return filterRegistration;
    }


}
