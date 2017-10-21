package com.iplay.component.pagination;

public class PaginationRequestValidationResult {
	private boolean valid;
	private String message;
	
	public PaginationRequestValidationResult(boolean valid, String message) {
		super();
		this.valid = valid;
		this.message = message;
	}
	
	public static PaginationRequestValidationResult fail(String message){
		return new PaginationRequestValidationResult(false, message);
	}
	
	public static PaginationRequestValidationResult succeed(){
		return new PaginationRequestValidationResult(true, "");
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public String getMessage() {
		return message;
	}
}
