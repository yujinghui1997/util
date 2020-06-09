package com.yjh.comp;

import cn.hutool.core.bean.BeanUtil;
import com.yjh.annotation.MyDecrypt;
import com.yjh.util.MyEncryptUtil;
import com.yjh.util.MyStrUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Arrays;
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
        // 参数类型
        Class<?> type = parameter.getParameterType();
        if (isBaseType(type)){
            // 参数名称
            String name = parameter.getParameterName();
            String value = webRequest.getParameter(name);
            boolean bean = BeanUtil.isBean(type);
            value = MyEncryptUtil.AESDecrypt(value);
            value = MyStrUtil.toJsonStr(value);
            return MyStrUtil.parseObj(value);
        }
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        // content-type : application/json
        if (parameter.hasParameterAnnotation(RequestBody.class)){
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
           return MyStrUtil.parseObj(sb.toString(),type);
        }

        if (type.isArray()){
            String parameter1 = webRequest.getParameter(parameter.getParameterName() + "[]");
            System.out.println(parameter1);
            return MyStrUtil.parseObj(parameter1,type);
        }
        // form
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String,Object> map = new HashMap<>();
        for (Map.Entry<String,String[]> entry : parameterMap.entrySet()){
            String value =  entry.getValue()[0];
            map.put(entry.getKey(),value);
        }
        String json = MyStrUtil.toJsonStr(map);
        return MyStrUtil.parseObj(json,type);
    }

    /**
     * 判断object是否为基本类型
     * @return
     */
    public static boolean isBaseType(Class clazz) {
        if (clazz.equals(java.lang.Integer.class) ||
                clazz.equals(java.lang.Byte.class) ||
                clazz.equals(java.lang.Long.class) ||
                clazz.equals(java.lang.Double.class) ||
                clazz.equals(java.lang.Float.class) ||
                clazz.equals(java.lang.Character.class) ||
                clazz.equals(java.lang.Short.class) ||
                clazz.equals(java.lang.Boolean.class)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String a = "[1,2,3]";
        String[] b = MyStrUtil.parseObj(a,String[].class);
        System.out.println(Arrays.toString(b));
    }

}
