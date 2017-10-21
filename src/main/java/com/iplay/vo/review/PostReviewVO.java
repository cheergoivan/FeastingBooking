package com.iplay.vo.review;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class PostReviewVO {
	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("5.0")
	private double rating;
	@NotEmpty
	private String review;
	
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
