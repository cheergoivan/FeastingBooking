package com.iplay.component.util;

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
}
