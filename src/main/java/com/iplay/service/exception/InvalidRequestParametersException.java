package com.iplay.service.exception;

public class InvalidRequestParametersException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRequestParametersException() {
		super();
	}

	public InvalidRequestParametersException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidRequestParametersException(String message) {
		super(message);
	}
	
}
