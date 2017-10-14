package com.iplay.service.exception;

public class ResourceForbiddenException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5110419395736130403L;
	
	public ResourceForbiddenException() {
		super();
	}

	public ResourceForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceForbiddenException(String message) {
		super(message);
	}
	
}
