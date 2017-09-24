package com.iplay.dto.hotel;

public class FeastDTO {
	private int id;
	private String name;
	private double price;
	private String[] courses;
	private String pictureUrls;
	
	public FeastDTO(int id, String name, double price, String[] courses, String pictureUrls) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.courses = courses;
		this.pictureUrls = pictureUrls;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String[] getCourses() {
		return courses;
	}
	public void setCourses(String[] courses) {
		this.courses = courses;
	}
	public String getPictureUrls() {
		return pictureUrls;
	}
	public void setPictureUrls(String pictureUrls) {
		this.pictureUrls = pictureUrls;
	}
}
