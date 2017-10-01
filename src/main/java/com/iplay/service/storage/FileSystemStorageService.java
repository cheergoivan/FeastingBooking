package com.iplay.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.configuration.storage.StorageConfigurationProperties;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@EnableConfigurationProperties(StorageConfigurationProperties.class)
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageConfigurationProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        if(!Files.exists(rootLocation)){
        	try {
				Files.createDirectories(rootLocation);
			} catch (IOException e) {
				throw new StorageException("Failed to create the storage directory " + properties.getLocation() , e);
			}
        }
    }

    @Override
    public String store(MultipartFile file,String filenameWithoutSuffix) {
    	String orignalName = file.getOriginalFilename(); String suffix = "";
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + orignalName);
            }
            int index = orignalName.lastIndexOf(".");
            if(index!=-1){
            	suffix = orignalName.substring(index).replace(";", "");
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filenameWithoutSuffix+suffix),StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        return filenameWithoutSuffix+suffix;
    }


    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

	@Override
	public boolean delete(String filename) {
		try {
			return Files.deleteIfExists(this.rootLocation.resolve(filename));
		} catch (IOException e) {
			throw new StorageException("Failed to delete file " + filename, e);
		}
	}

	@Async
	public void delete(List<String> files) {
		files.forEach(f->{
			delete(f);
		});
	}

	@Async
	public void delete(String[] files) {
		for(String f:files){
			delete(f);
		}
	}
}
