package com.yjh.core;

public enum  MyHttpStatus {
    SUCCESS(200),
    TOKEN_ERR(201),
    REQUEST_ERR(400),
    PARAM_ERR(401),
    FAIL(500);
    private Integer code;
    MyHttpStatus(Integer code){
        this.code = code;
    }
    public Integer getCode(){
        return this.code;
    }
}
