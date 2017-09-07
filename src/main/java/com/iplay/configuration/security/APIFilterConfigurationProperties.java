package com.iplay.configuration.security;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iplay.api")
public class APIFilterConfigurationProperties {
	/**
	 * APIs which don't require any authentication
	 */
	private List<String> whiteList;
	
	/**
	 * Specify URL pattern which is only allowed accessing by authenticated users
	 */
	private String protectedResources;

	public List<String> getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(List<String> whiteList) {
		this.whiteList = whiteList;
	}

	public String getProtectedResources() {
		return protectedResources;
	}

	public void setProtectedResources(String protectedResources) {
		this.protectedResources = protectedResources;
	}
}
