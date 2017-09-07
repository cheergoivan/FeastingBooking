package com.iplay.entity.hotel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banquet_hall")
public class BanquetHallDO {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double area;
	private int minimumTables;
	private int maximumTables;
	private double minimumPrice;
	private int numOfPictures;

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

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getMinimumTables() {
		return minimumTables;
	}

	public void setMinimumTables(int minimumTables) {
		this.minimumTables = minimumTables;
	}

	public int getMaximumTables() {
		return maximumTables;
	}

	public void setMaximumTables(int maximumTables) {
		this.maximumTables = maximumTables;
	}

	public double getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public int getNumOfPictures() {
		return numOfPictures;
	}

	public void setNumOfPictures(int numOfPictures) {
		this.numOfPictures = numOfPictures;
	}
}
