package com.iplay.configuration.security;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<String> getWhiteListUrls(){
		return whiteList.stream().map(l->l.contains("::")?l.substring(l.indexOf("::")+2):l).collect(Collectors.toList());
	}
}
