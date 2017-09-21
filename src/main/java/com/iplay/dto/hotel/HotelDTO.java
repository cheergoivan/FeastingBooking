package com.iplay.dto.hotel;

import java.util.List;

public class HotelDTO {
	private int id;
	private String name;
	private String description;
	private String address;
	private String contact;
	private String telephone;
	private String email;
	private int[] tableRange;
	private double[] priceRange;
	private double rating;
	private List<SimplifiedBanquetHallDTO> banquetHalls;
	private List<SimplifiedFeastDTO> feasts;
	private String[] pictureUrls;
	
	public HotelDTO(int id, String name, String description, String address, String contact, String telephone,
			String email, int[] tableRange, double[] priceRange, double rating,
			List<SimplifiedBanquetHallDTO> banquetHalls, List<SimplifiedFeastDTO> feasts, String[] pictureUrls) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.contact = contact;
		this.telephone = telephone;
		this.email = email;
		this.tableRange = tableRange;
		this.priceRange = priceRange;
		this.rating = rating;
		this.banquetHalls = banquetHalls;
		this.feasts = feasts;
		this.pictureUrls = pictureUrls;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int[] getTableRange() {
		return tableRange;
	}

	public void setTableRange(int[] tableRange) {
		this.tableRange = tableRange;
	}

	public double[] getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(double[] priceRange) {
		this.priceRange = priceRange;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<SimplifiedBanquetHallDTO> getBanquetHalls() {
		return banquetHalls;
	}

	public void setBanquetHalls(List<SimplifiedBanquetHallDTO> banquetHalls) {
		this.banquetHalls = banquetHalls;
	}

	public List<SimplifiedFeastDTO> getFeasts() {
		return feasts;
	}

	public void setFeasts(List<SimplifiedFeastDTO> feasts) {
		this.feasts = feasts;
	}

	public String[] getPictureUrls() {
		return pictureUrls;
	}

	public void setPictureUrls(String[] pictureUrls) {
		this.pictureUrls = pictureUrls;
	}
}
