package com.iplay.vo.recommendation;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class RecommendationVO {
	@NotNull
	private int hotelId;
	@NotNull
	private MultipartFile file;
	
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
