package com.iplay.entity.recommendation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "recommendation")
public class RecommendationDO {
	@Id
	@GeneratedValue
	private int id;
	private int hotelId;
	private String picture;
	
	public RecommendationDO(){}
	
	public RecommendationDO(int hotelId, String picture) {
		super();
		this.hotelId = hotelId;
		this.picture = picture;
	}

	public RecommendationDO(int id, int hotelId, String picture) {
		super();
		this.id = id;
		this.hotelId = hotelId;
		this.picture = picture;
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
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
}
