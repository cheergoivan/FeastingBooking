package com.iplay.entity.hotel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "feast")
public class FeastDO {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double price;
	private String courses;
	private int numOfPictures;
	
	public FeastDO(){}
	
	public FeastDO(String name, double price, String courses, int numOfPictures) {
		super();
		this.name = name;
		this.price = price;
		this.courses = courses;
		this.numOfPictures = numOfPictures;
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
	public int getNumOfPictures() {
		return numOfPictures;
	}
	public void setNumOfPictures(int numOfPictures) {
		this.numOfPictures = numOfPictures;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
