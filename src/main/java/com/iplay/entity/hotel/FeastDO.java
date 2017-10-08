package com.iplay.entity.hotel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iplay.component.util.DelimiterUtils;

@Entity
@Table(name = "feast")
public class FeastDO {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double price;
	@Column(length=1000)
	private String courses;
	@Column(length=1000)
	private String pictures = "";
	
	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private HotelDO hotelDO;
	
	public FeastDO(){}
	
	public FeastDO(String name, double price, String courses, String pictures) {
		super();
		this.name = name;
		this.price = price;
		this.courses = courses;
		this.pictures = pictures;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCourses() {
		return courses;
	}
	public void setCourses(String courses) {
		this.courses = courses;
	}
	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	public String[] getPicturesAsArray(){
		if(pictures.equals(""))
			return new String[0];
		return pictures.split(DelimiterUtils.PICTURE_DELIMITER);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HotelDO getHotelDO() {
		return hotelDO;
	}

	public void setHotelDO(HotelDO hotelDO) {
		this.hotelDO = hotelDO;
	}
}
