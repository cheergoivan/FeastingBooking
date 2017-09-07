package com.iplay.dto;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private boolean isSuccess;
	private T data;
	public static final ApiResponse<String> SUCCESSFUL_RESPONSE_WITHOUT_MESSAGE;
	
	static {
		SUCCESSFUL_RESPONSE_WITHOUT_MESSAGE = new ApiResponse<String>();
		SUCCESSFUL_RESPONSE_WITHOUT_MESSAGE.setSuccess(true);
	}
	
	public static ApiResponse<String> createFailApiResponse(String reason) {
		ApiResponse<String> result = new ApiResponse<String>();
		result.setSuccess(false);
		result.setData(reason);
		return result;
	} 
	
	public static <T> ApiResponse<T> createSuccessApiResponse(T data) {
		ApiResponse<T> result = new ApiResponse<T>();
		result.setSuccess(true);
		result.setData(data);
		return result;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
}

