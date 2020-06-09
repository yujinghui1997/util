package com.yjh.comp;

import com.alibaba.fastjson.JSON;
import com.yjh.annotation.MyDecrypt;
import com.yjh.util.MyEncryptUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter method) {
        Class<?> aClass = method.getDeclaringClass();
        Boolean isde = false;
        if (aClass.isAnnotationPresent(MyDecrypt.class)){
            isde = aClass.getAnnotation(MyDecrypt.class).value();
        }
        if (method.hasMethodAnnotation(MyDecrypt.class)){
            isde = method.getMethodAnnotation(MyDecrypt.class).value();
        }
        return isde;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Map<String,Object> map = new HashMap<>();
        return request;
    }


}
