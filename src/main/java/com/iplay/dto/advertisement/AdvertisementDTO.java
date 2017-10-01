package com.iplay.dto.advertisement;

public class AdvertisementDTO {
	private int id;
	private String pictureUrl;
	
	public AdvertisementDTO(int id, String pictureUrl) {
		super();
		this.id = id;
		this.pictureUrl = pictureUrl;
	}
	
	public AdvertisementDTO(){}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
