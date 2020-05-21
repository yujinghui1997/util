package com.yjh.core;

import io.jsonwebtoken.JwtException;

public class MyJwtException extends JwtException {

    public MyJwtException(String msg){
        super(msg);
    }
    public MyJwtException(String msg,Throwable throwable){
        super(msg,throwable);
    }

}
