package com.yjh.core;

public class ResData<T> {

	private Integer code ;
	private boolean status ;
	private String message;
	private T data;
	
	public static<T> ResData<T> ok(T data) {
		return new ResData(MyHttpStatus.OK,true,"SUCCESS",data);
	}
	public static<T> ResData<T> err(String message) {
		return err(MyHttpStatus.SERVER_ERR,message);
	}

	public static<T> ResData<T> err(MyHttpStatus status,String message) {
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
