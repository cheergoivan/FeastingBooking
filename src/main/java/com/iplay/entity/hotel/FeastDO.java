package com.iplay.entity.hotel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	private String courses;
	private String pictures;
	
	public FeastDO(){
		pictures = "";
	}
	
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
		return pictures.split(DelimiterUtils.PICTURE_DELIMITER);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
