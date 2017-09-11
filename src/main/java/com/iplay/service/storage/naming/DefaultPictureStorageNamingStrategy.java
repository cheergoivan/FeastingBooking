package com.iplay.service.storage.naming;

import org.springframework.stereotype.Service;

@Service
public class DefaultPictureStorageNamingStrategy implements StorageNamingStrategy{

	@Override
	public String generateResourceName(String entityType, int entityId, int pictureId) {
		return entityType+"_"+entityId+"_"+pictureId+".jpg";
	}
	
}
