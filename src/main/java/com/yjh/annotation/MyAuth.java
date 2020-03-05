package com.yjh.annotation;

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
    /** 检查token **/
    boolean authToken() default false;
    /** 检验浏览器 **/
    boolean authBrowser() default false;

}
