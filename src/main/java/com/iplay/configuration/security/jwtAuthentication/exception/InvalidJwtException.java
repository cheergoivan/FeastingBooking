package com.iplay.configuration.security.jwtAuthentication.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {

	private static final long serialVersionUID = -294671188037098603L;

	public InvalidJwtException(String msg) {
		super(msg);
	}
}
