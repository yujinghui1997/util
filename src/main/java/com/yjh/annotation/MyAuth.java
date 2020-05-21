package com.yjh.annotation;

import com.yjh.util.MyJwtUtil;

import java.lang.annotation.*;

/**
 * 用来检验接口
 * 认证
 * @author Administrator
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAuth {
    boolean authToken() default true;
}
