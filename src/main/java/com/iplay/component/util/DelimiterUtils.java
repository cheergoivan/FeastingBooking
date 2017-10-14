package com.iplay.component.util;

import org.springframework.util.StringUtils;

public class DelimiterUtils {
	
	public static final String PICTURE_DELIMITER = ";";
	
	public static String joinArray(String[] src, String delimiter){
		StringBuilder sb = new StringBuilder();
		for(String s:src){
			sb.append(s);
			sb.append(delimiter);
		}
		return sb.toString();
	}
	
	public static String[] split(String src, String delimiter){
		if(StringUtils.isEmpty(src))
			return new String[0];
		return src.split(delimiter);
	}
}
