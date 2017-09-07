package com.iplay.configuration.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iplay.jwt")
public class JwtConfigurationProperties {
	
	/**
	 * Secret key to generate a signature of a json web token.
	 */
	private String secretKey;
	
	/**
	 * Expiration time of a token, time unit is minute.If you set it as -1,
	 * the token will never expire.
	 */
	private Long tokenExpirationTime;
	
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Long getTokenExpirationTime() {
		return tokenExpirationTime;
	}

	public void setTokenExpirationTime(Long tokenExpirationTime) {
		this.tokenExpirationTime = tokenExpirationTime;
	}
}
