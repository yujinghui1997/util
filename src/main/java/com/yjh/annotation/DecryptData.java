package com.yjh.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecryptData {

    boolean value() default true;
}
