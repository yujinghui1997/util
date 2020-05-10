package com.yjh.core;

import cn.hutool.http.HttpStatus;

public class ResData<T> {

	public static final ResData SUCCEESS = ResData.success(null);
	public static final ResData FAIL = ResData.fail("FAIL");
	private Integer code ;
	private boolean status ;
	private String message;
	private T data;

	public static<T> ResData<T> success(T data) {
		return new ResData(MyHttpStatus.SUCCESS.getCode(),true,"SUCCESS",data);
	}
	public static<T> ResData<T> fail(String message) {
		return fail(MyHttpStatus.FAIL,message);
	}
	public static<T> ResData<T> fail(MyHttpStatus status,String message) {
		return new ResData(status.getCode(),false,message,null);
	}

	private ResData(Integer code,Boolean status,String message,T data){
		this.code = code;
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public Integer getCode() {
		return code;
	}
	public boolean isStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public T getData() {
		return data;
	}


}
