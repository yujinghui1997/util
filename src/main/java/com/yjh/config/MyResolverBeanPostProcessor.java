package com.yjh.config;

import com.yjh.comp.MyHandlerMethodArgumentResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class MyResolverBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private MyHandlerMethodArgumentResolver myHandlerMethodArgumentResolver;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("requestMappingHandlerAdapter")){
            RequestMappingHandlerAdapter adapter = (RequestMappingHandlerAdapter)bean;
            List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();
            argumentResolvers = addArgumentResolvers(argumentResolvers);
            adapter.setArgumentResolvers(argumentResolvers);
        }
        return bean;
    }

    private List<HandlerMethodArgumentResolver> addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(myHandlerMethodArgumentResolver);
        resolvers.addAll(argumentResolvers);
        return resolvers;
    }
}
