package com.iplay.configuration.totp;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iplay.totp")
public class TotpConfigurationProperties {
	/**
	 * Time size of a time window and the default value is 30s.
	 */
	private long timeWindowSize = 30;

	/**
	 * Unix time to start counting time steps.
	 */
	private long timeStart = 0L;

	/**
	 * The hash algorithm in HMAC(keyed-hash message authentication code). The
	 * default value is HmacSHA1. Optional value:HmacSHA256,HmacSHA512
	 */
	private String cryptoAlgorithm = "HmacSHA1";

	/**
	 * If you set allowedPassedValidationWindows=1, this would mean the
	 * validator could perform not only a validation against the current time window 
	 * but also one more validations for the passed time window (for a total of 2
	 * validations).
	 * 
	 * The default value is 10, which means the totp is valid in 5 minutes if
	 * the time window size is 30s.
	 */
	private int allowedPastValidationWindows = 10; 
	
	/**
	 * If you set allowedFutureValidationWindows=1, this would mean the
	 * validator would perform not only a validation against the current 
	 * time window but also one more validations for the next time window
	 * (for a total of 2 validations).
	 * 
	 * The default value is 1.
	 */
	private int allowedFutureValidationWindows = 1;

	/**
	 * The length of the time-based one-time password and the default value is 6.
	 * 
	 */
	private int TotpLength = 6;

	public long getTimeWindowSize() {
		return timeWindowSize;
	}

	public void setTimeWindowSize(long timeWindowSize) {
		this.timeWindowSize = timeWindowSize;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public String getCryptoAlgorithm() {
		return cryptoAlgorithm;
	}

	public void setCryptoAlgorithm(String cryptoAlgorithm) {
		this.cryptoAlgorithm = cryptoAlgorithm;
	}

	public int getAllowedPastValidationWindows() {
		return allowedPastValidationWindows;
	}

	public void setAllowedPastValidationWindows(int allowedPastValidationWindows) {
		this.allowedPastValidationWindows = allowedPastValidationWindows;
	}

	public int getAllowedFutureValidationWindows() {
		return allowedFutureValidationWindows;
	}

	public void setAllowedFutureValidationWindows(int allowedFutureValidationWindows) {
		this.allowedFutureValidationWindows = allowedFutureValidationWindows;
	}

	public int getTotpLength() {
		return TotpLength;
	}

	public void setTotpLength(int totpLength) {
		TotpLength = totpLength;
	}
}
