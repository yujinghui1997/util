package com.yjh.comp;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.yjh.annotation.MyAuth;
import com.yjh.core.MyHttpStatus;
import com.yjh.core.MyJwtException;
import com.yjh.core.ResData;
import com.yjh.properties.MyJwtProperties;
import com.yjh.util.MyJwtUtil;
import com.yjh.util.MyStrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class MyAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MyJwtProperties myJwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 获取方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 获取方法的类
        Class<?> methodClass = method.getDeclaringClass();
        Boolean isAuth = false;
        if (methodClass.isAnnotationPresent(MyAuth.class)) {
            isAuth = methodClass.getAnnotation(MyAuth.class).authToken();
        }
        if (method.isAnnotationPresent(MyAuth.class)) {
            isAuth = method.getAnnotation(MyAuth.class).authToken();
        }
        // 判断访问的后台接口 有没有 checkToken 注解 没有直接放行
        if (isAuth) {
            String token = request.getHeader(myJwtProperties.getHeadToken());
            if (StrUtil.hasBlank(token)) {
                throw new MyJwtException("没有令牌，不可访问");
            }
            String username = request.getHeader(myJwtProperties.getHeadName());
            if (StrUtil.hasBlank(username)) {
                throw new MyJwtException("匿名用户，不可访问");
            }
            boolean isVerify = MyJwtUtil.authToken(myJwtProperties.getKey(), token, username);
            if (!isVerify) {
                throw new MyJwtException("无效令牌，不可访问");
            }
            return true;
        }
        return true;
    }
}
