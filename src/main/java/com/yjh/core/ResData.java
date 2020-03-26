package com.yjh.core;

import cn.hutool.http.HttpStatus;

public class ResData<T> {

	public static final ResData SUCCEESS = ResData.success(null);
	public static final ResData FaIL = ResData.fail("FAIL");
	private Integer code ;
	private boolean status ;
	private String message;
	private T data;

	public static<T> ResData<T> success(T data) {
		return new ResData(MyHttpStatus.SUCCESS,true,"SUCCESS",data);
	}
	public static<T> ResData<T> fail(String message) {
		return fail(MyHttpStatus.FAIL,message);
	}
	public static<T> ResData<T> fail(MyHttpStatus status,String message) {
		return new ResData(status,false,message,null);
	}

	private ResData(MyHttpStatus code,Boolean status,String message,T data){
		this.code = code.getCode();
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
