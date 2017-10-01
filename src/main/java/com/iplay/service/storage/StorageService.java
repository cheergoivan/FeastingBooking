package com.iplay.service.storage;

import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	/**
	 * return the filename with suffix
	 * 
	 * @param file
	 * @param filenameWithoutSuffix
	 * @return
	 */
	String store(MultipartFile file, String filenameWithoutSuffix);

	Path load(String filename);

	Resource loadAsResource(String filename);

	/**
	 * @param filename
	 * @return {@code true} if the file was deleted by this method; {@code
	*          false} if the file could not be deleted because it did not exist
	 */
	boolean delete(String filename);
	
	void delete(List<String> files);
	
	void delete(String[] files);
}
