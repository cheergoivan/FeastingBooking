package com.iplay.configuration.security.jwtAuthentication.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.iplay.configuration.security.jwtAuthentication.exception.InvalidJwtException;

@Component
public class TokenExtractor {
	private static final String JWT_TOKEN_HEADER_PARAM = "Authorization";

	private static final String JWT_TOKEN_HEADER = "Bearer ";
	
	public static String extract(HttpServletRequest request){
		return extract(request.getHeader(JWT_TOKEN_HEADER_PARAM));
	}
	
	public static String extract(String token){
		if (token == null || token.length() < JWT_TOKEN_HEADER.length())
			throw new InvalidJwtException("Token not found!");
		if(!token.startsWith(JWT_TOKEN_HEADER))
			throw new InvalidJwtException("Invalid Token! A token must begin with "+"\""+JWT_TOKEN_HEADER+"\"");
		token = token.substring(JWT_TOKEN_HEADER.length());
		return token;
	}
}
