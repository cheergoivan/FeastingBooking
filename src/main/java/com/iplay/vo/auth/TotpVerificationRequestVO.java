package com.iplay.vo.auth;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class TotpVerificationRequestVO {
	@NotBlank(message="email address mustn't be blank")
	@Size(max = 255)
	private String email;
	@NotBlank(message="totp mustn't be blank")
	@Size(max = 255)
	private String totp;
	
	public TotpVerificationRequestVO(){}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTotp() {
		return totp;
	}

	public void setTotp(String totp) {
		this.totp = totp;
	}
}
