package com.iplay.service.storage;

import org.springframework.stereotype.Service;

@Service
public class DefaultPictureStorageStrategy implements StorageStrategy{

	@Override
	public String generateResourceName(String entityType, int entityId, int pictureId) {
		return null;
	}
	
}
