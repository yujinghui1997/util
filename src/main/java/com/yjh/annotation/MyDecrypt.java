package com.yjh.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyDecrypt {

    boolean value() default true;
}
