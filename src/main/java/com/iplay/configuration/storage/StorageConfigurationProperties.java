package com.iplay.configuration.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("iplay.storage")
public class StorageConfigurationProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
