package com.iplay.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.iplay.web.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException e) {
		System.out.println(e.getMessage());
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
