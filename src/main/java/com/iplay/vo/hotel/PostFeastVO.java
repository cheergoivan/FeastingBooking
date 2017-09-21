package com.iplay.vo.hotel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class PostFeastVO {
	private int id = -1;
	@NotBlank(message="The name of the banquet hall can't be blank")
	private String name;
	@NotNull(message="The price of the banquet hall can't be blank")
	private double price;
	@NotBlank(message="The courses the banquet hall can't be blank")
	private String courses;
	@NotEmpty(message="Pictures can not be empty")
	@Size(min=1,message="The quantity of pictures must be at least 1")
	private MultipartFile[] files;
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
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
}
