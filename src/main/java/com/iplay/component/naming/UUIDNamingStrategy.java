package com.iplay.component.naming;

import java.util.UUID;

public class UUIDNamingStrategy {

	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
