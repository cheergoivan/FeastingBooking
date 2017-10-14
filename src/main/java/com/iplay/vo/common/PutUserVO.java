package com.iplay.vo.common;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PutUserVO {
	@NotEmpty
	@Size(max = 255)
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
