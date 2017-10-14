package com.iplay.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.iplay.service.exception.ResourceForbiddenException;
import com.iplay.service.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<String> handleResourceForbidden(ResourceForbiddenException e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
