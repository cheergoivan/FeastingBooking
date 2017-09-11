package com.iplay.service.storage;

public interface StorageStrategy {
	
	String generateResourceName(String entityType, int entityId, int pictureId);
	
}
