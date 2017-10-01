package com.iplay.entity.advertisement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "advertisement")
public class AdvertisementDO {
	@Id
	@GeneratedValue
	private int id;
	private String picture;
	
	public AdvertisementDO(){}
	
	public AdvertisementDO(String picture) {
		super();
		this.picture = picture;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
}
