package com.yjh.comp;

import com.yjh.annotation.MyEncrypt;
import com.yjh.core.ResData;
import com.yjh.util.MyEncryptUtil;
import com.yjh.util.MyStrUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@RestControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice<ResData> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Class<?> parent = methodParameter.getDeclaringClass();
        Boolean isDe = false;
        if (parent.isAnnotationPresent(MyEncrypt.class)){
            isDe =  parent.getAnnotation(MyEncrypt.class).value();
        }
        if (methodParameter.hasMethodAnnotation(MyEncrypt.class)){
            isDe = methodParameter.getMethodAnnotation(MyEncrypt.class).value();
        }
        return isDe;
    }

    @Override
    public ResData beforeBodyWrite(ResData resData, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
       if (resData.getCode() == 200){
           Object data = resData.getData();
           if (data != null){
               String s = MyStrUtil.toJsonStr(data);
               return ResData.success(MyEncryptUtil.AESEncrypt(s));
           }
       }
        return resData;
    }




}
