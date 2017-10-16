package com.iplay.entity.review;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "review",indexes={@Index(name="IDX_HOTEL",columnList="hotelId")})
public class ReviewDO {
	@Id
	@GeneratedValue
	private int id;
	private int hotelId;
	private String banquetHall;
	private int authorId;
	private String author;
	private double rating;
	private String review;
	
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public String getBanquetHall() {
		return banquetHall;
	}
	public void setBanquetHall(String banquetHall) {
		this.banquetHall = banquetHall;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
}
