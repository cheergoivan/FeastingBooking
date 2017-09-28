package com.iplay.dto.auth;

public class AuthResponseDTO {
	
	private UserDTO user;
	private String token;

	public AuthResponseDTO(){}
	
	public AuthResponseDTO(UserDTO user, String token) {
		super();
		this.user = user;
		this.token = token;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
