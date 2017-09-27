package com.iplay.configuration.security.jwtAuthentication.auth;

import com.iplay.entity.user.Role;

public class UserContext {
	private int userId;
	
	private String username;
	
	private Role role;
	
	public UserContext(){} 
	
	public UserContext(int userId, String username, Role role) {
		super();
		this.userId = userId;
		this.username = username;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserContext [userId=" + userId + ", username=" + username + ", role=" + role + "]";
	}
}
