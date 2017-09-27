package com.iplay.dto.auth;

public class JwtResponseDTO {
	private String role;
	private String token;

	public JwtResponseDTO(){}
	
	public JwtResponseDTO(String role, String token) {
		super();
		this.role = role;
		this.token = token;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
