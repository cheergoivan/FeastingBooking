package com.iplay.dto.recommendation;

public class RecommendationDTO {
	private int id;
	private int hotelId;
	private String pictureUrl;
	
	public RecommendationDTO(int id, int hotelId, String pictureUrl) {
		super();
		this.id = id;
		this.hotelId = hotelId;
		this.pictureUrl = pictureUrl;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
