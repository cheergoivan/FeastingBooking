package com.iplay.service.storage.naming;

public interface StorageNamingStrategy {
	
	String generateResourceName(String entityType, int entityId, int resourceId);
	
}
