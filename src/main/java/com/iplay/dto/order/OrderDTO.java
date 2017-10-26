package com.iplay.dto.order;

import com.iplay.entity.order.OrderStatus;

public class OrderDTO {
	private int id;
	//differ from Do
	private String creationTime;
	
	private long orderNumber;
	private String banquetHall;
	private String hotel;
	private String customer;
	private int tables;
	//differ from Do
	private String[] candidateDates;
	private String recommender;
	private String contact;
	private String phone;
	private String manager;
	private String feastingDate;
	private OrderStatus orderStatus;
	private boolean reviewed;
	//differ from Do
	private String lastUpdated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getBanquetHall() {
		return banquetHall;
	}

	public void setBanquetHall(String banquetHall) {
		this.banquetHall = banquetHall;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getTables() {
		return tables;
	}

	public void setTables(int tables) {
		this.tables = tables;
	}

	public String[] getCandidateDates() {
		return candidateDates;
	}

	public void setCandidateDates(String[] candidateDates) {
		this.candidateDates = candidateDates;
	}

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getFeastingDate() {
		return feastingDate;
	}

	public void setFeastingDate(String feastingDate) {
		this.feastingDate = feastingDate;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public boolean isReviewed() {
		return reviewed;
	}

	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
