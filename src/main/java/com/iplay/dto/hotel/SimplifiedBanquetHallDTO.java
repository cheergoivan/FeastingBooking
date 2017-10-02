package com.iplay.dto.hotel;

public class SimplifiedBanquetHallDTO {
	private int id;
	private String name;
	private double area;
	private int[] tableRange;
	private double minimumPrice;
	private String pictureUrl;
	
	public SimplifiedBanquetHallDTO(int id, String name, double area, int[] tableRange, double minimumPrice, String pictureUrl) {
		super();
		this.id = id;
		this.name = name;
		this.area = area;
		this.tableRange = tableRange;
		this.minimumPrice = minimumPrice;
		this.pictureUrl = pictureUrl;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public int[] getTableRange() {
		return tableRange;
	}
	public void setTableRange(int[] tableRange) {
		this.tableRange = tableRange;
	}

	public double getMinimumPrice() {
		return minimumPrice;
	}
	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
