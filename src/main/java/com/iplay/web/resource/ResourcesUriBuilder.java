package com.iplay.web.resource;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public class ResourcesUriBuilder {
	public static String buildtUri(String name){
		return name==null?null: MvcUriComponentsBuilder
        .fromMethodName(ResourceController.class, "serveFile",name)
        .build().toString();
	}
	
	public static String[] buildUris(String[] names){
		String[] uris = new String[names.length];
		for(int i=0;i<names.length;i++){
			uris[i] = buildtUri(names[i]);
		}
		return uris;
	}
}
