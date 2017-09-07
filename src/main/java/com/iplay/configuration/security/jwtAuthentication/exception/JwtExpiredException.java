package com.iplay.configuration.security.jwtAuthentication.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;
    
    public JwtExpiredException(String msg) {
        super(msg);
    }
    
}
