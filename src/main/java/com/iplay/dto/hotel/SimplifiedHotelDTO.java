package com.iplay.dto.hotel;

public class SimplifiedHotelDTO {
	private int id;
	private String name;
	private double[] priceRange;
	private int[] tableRange;
	private String districtOfAddress;
	private int numOfComment;
	private String pictureUrl;
	

	public SimplifiedHotelDTO(int id, String name, double[] priceRange, int[] tableRange, String districtOfAddress, int numOfComment,
			String pictureUrl) {
		super();
		this.id = id;
		this.name = name;
		this.priceRange = priceRange;
		this.tableRange = tableRange;
		this.districtOfAddress = districtOfAddress;
		this.numOfComment = numOfComment;
		this.pictureUrl = pictureUrl;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getDistrictOfAddress() {
		return districtOfAddress;
	}

	public void setDistrictOfAddress(String districtOfAddress) {
		this.districtOfAddress = districtOfAddress;
	}
}
