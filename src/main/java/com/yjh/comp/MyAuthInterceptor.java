package com.yjh.comp;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.yjh.annotation.MyAuth;
import com.yjh.config.MyJwtProperties;
import com.yjh.core.MyHttpStatus;
import com.yjh.core.ResData;
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
        if (!(handler instanceof Method)) {
            return true;
        }
        // 获取方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断访问的后台接口 有没有 checkToken 注解 没有直接放行
        if (method.isAnnotationPresent(MyAuth.class)) {
            MyAuth myAuth = method.getAnnotation(MyAuth.class);
            // 判断checkToken注解标记的方法 默认值 是否为 true
            if (myAuth.authToken()) {
                String msg = "";
                String token = request.getHeader(myJwtProperties.getHeadToken());
                if (StrUtil.hasBlank(token)) {
                    msg = "没有令牌，不可访问";
                }
                String username = request.getHeader(myJwtProperties.getHeadName());
                if (StrUtil.hasBlank(username)) {
                    msg = "匿名用户，不可访问";
                }
                boolean isVerify = MyJwtUtil.authToken(myJwtProperties.getKey(), token, username);
                if (!isVerify) {
                    msg = "无效令牌，不可访问";
                }
                if (StrUtil.hasBlank(msg)){
                    PrintWriter writer =  response.getWriter();
                    ResData data = ResData.fail(MyHttpStatus.TOKEN_ERR,msg);
                    String json = MyStrUtil.toJsonStr(data);
                    writer.println(json);
                    writer.flush();
                    writer.close();
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}