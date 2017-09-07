package com.iplay.dto.auth;

public class TotpVerificationResponseDTO {
	
	private boolean isTotpValid;
	
	private String token;

	public boolean isTotpValid() {
		return isTotpValid;
	}

	public void setTotpValid(boolean isTOTPValid) {
		this.isTotpValid = isTOTPValid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
