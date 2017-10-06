package com.iplay.dto.hotel;

public class SimplifiedHotelDTO {
	private int id;
	private String name;
	private double[] priceRange;
	private int[] tableRange;
	private int numOfComment;
	private String pictureUrl;

	public SimplifiedHotelDTO(int id, String name, double[] priceRange, int[] tableRange, int numOfComment,
			String pirctureUrl) {
		super();
		this.id = id;
		this.name = name;
		this.priceRange = priceRange;
		this.tableRange = tableRange;
		this.numOfComment = numOfComment;
		this.pictureUrl = pirctureUrl;
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

	public double[] getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(double[] priceRange) {
		this.priceRange = priceRange;
	}

	public int[] getTableRange() {
		return tableRange;
	}

	public void setTableRange(int[] tableRange) {
		this.tableRange = tableRange;
	}

	public int getNumOfComment() {
		return numOfComment;
	}

	public void setNumOfComment(int numOfComment) {
		this.numOfComment = numOfComment;
	}

	public String getPirctureUrl() {
		return pictureUrl;
	}

	public void setPirctureUrl(String pirctureUrl) {
		this.pictureUrl = pirctureUrl;
	}
}
