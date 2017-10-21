package com.iplay.web;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.iplay.service.exception.InvalidRequestParametersException;
import com.iplay.service.exception.ResourceForbiddenException;
import com.iplay.service.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFound(ResourceNotFoundException e) {
		return e.getMessage();
	}

	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleResourceForbidden(ResourceForbiddenException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleResourceForbidden(InvalidRequestParametersException e) {
		return e.getMessage();
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	/**
	 * This is the exception handler for RequestParam(@Valid) in controller(@Validated)
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handle(ConstraintViolationException e) {
		for(ConstraintViolation<?> c:e.getConstraintViolations()){
	        return "Bad request: " +c.getMessage();
	    }
		return "Bad request!";
	}

	/*
	 * @ExceptionHandler(ResourceForbiddenException.class) public
	 * ResponseEntity<String> handleResourceForbidden(ResourceForbiddenException
	 * e) { return new ResponseEntity<String>(e.getMessage(),
	 * HttpStatus.FORBIDDEN); }
	 */
}
