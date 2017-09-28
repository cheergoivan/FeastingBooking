package com.iplay.web.resource;

import java.util.LinkedList;
import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

public class ResourcesUriBuilder {
	public static String buildUri(String name){
		return (name==null||name=="")?null: MvcUriComponentsBuilder
        .fromMethodName(ResourceController.class, "serveFile",name)
        .build().toString();
	}
	
	public static String[] buildUris(String[] names){
		List<String> uris = new LinkedList<>();
		for(int i=0;i<names.length;i++){
			String uri = buildUri(names[i]);
			if(uri!=null){
				uris.add(uri);
			}
		}
		return uris.toArray(new String[0]);
	}
}
