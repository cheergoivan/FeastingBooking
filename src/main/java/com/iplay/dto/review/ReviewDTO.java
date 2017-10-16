package com.iplay.dto.review;

public class ReviewDTO {
	private int id;
	private int hotelId;
	private String banquetHall;
	private int authorId;
	private String author;
	private double rating;
	private String review;
	
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
	public String getBanquetHall() {
		return banquetHall;
	}
	public void setBanquetHall(String banquetHall) {
		this.banquetHall = banquetHall;
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
