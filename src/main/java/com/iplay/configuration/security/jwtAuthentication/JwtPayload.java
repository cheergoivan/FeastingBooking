package com.iplay.configuration.security.jwtAuthentication;

import com.iplay.entity.user.Role;

public class JwtPayload {
	public static final String CLAIM_KEY_USERID = "id";

	public static final String CLAIM_KEY_ROLE = "role";

	private String subject;
	private int userId;
	private Role role;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
