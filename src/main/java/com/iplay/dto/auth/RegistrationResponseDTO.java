package com.iplay.dto.auth;

public class RegistrationResponseDTO {
	private long userId;
	private String token;

	public RegistrationResponseDTO(long userId, String token) {
		super();
		this.userId = userId;
		this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
