package com.iplay.vo.hotel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class PostFeastVO {
	private int id = -1;
	@NotBlank(message="The name of the feast can't be blank")
	@Size(max = 255)
	private String name;
	@NotNull(message="The price of the feast can't be blank")
	private double price;
	@NotBlank(message="The courses the feast can't be blank")
	private String courses;

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
	public String getCourses() {
		return courses;
	}
	public void setCourses(String courses) {
		this.courses = courses;
	}
}
