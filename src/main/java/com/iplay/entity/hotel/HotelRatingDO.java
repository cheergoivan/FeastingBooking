package com.iplay.entity.hotel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hotel_rating")
public class HotelRatingDO {
	@Id
	private int id;
	private double totalScore;
	private int times;
	
	public HotelRatingDO(int id, double totalScore, int times) {
		super();
		this.id = id;
		this.totalScore = totalScore;
		this.times = times;
	}
	
	public HotelRatingDO(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
}
