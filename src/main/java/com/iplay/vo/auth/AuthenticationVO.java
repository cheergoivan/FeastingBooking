package com.iplay.vo.auth;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class AuthenticationVO {
	@NotBlank(message="username mustn't be blank")
	@Size(max = 255)
	private String username;
	@NotBlank(message="password mustn't be blank")
	@Size(max = 255)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
