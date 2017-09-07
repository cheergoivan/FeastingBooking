package com.iplay.service.storage;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	
	  	void store(MultipartFile file,String filename);
	  	
	    Path load(String filename);

	    Resource loadAsResource(String filename);
}
