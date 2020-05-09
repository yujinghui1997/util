package com.yjh.core;

public class MyException extends Exception {

    private Integer code;
    private String msg;

    public MyException(MyHttpStatus status,String msg){
        super(msg);
        this.code = status.getCode();
        this.msg = msg;
    }

    public MyException(String msg){
        super(msg);
        this.code = MyHttpStatus.FAIL.getCode();
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}
