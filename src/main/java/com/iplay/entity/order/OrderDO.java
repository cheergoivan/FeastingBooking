package com.iplay.entity.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "feast_order",indexes={@Index(name="IDX_CUSTOMER",columnList="customerId"),
		@Index(name="IDX_RECOMMENDER",columnList="recommenderId"),
		@Index(name="IDX_MANAGER",columnList="managerId")
		})
public class OrderDO {
	@Id
	@GeneratedValue
	private int id;
	private long creationTime;
	
	private long orderNumber;

	private int banquetHallId;
	private String banquetHall;
	private int hotelId;
	private String hotel;
	private int customerId;
	private String customer;
	private int tables;
	private String candidateDates;
	private int recommenderId;
	private String recommender;
	private String contact;
	private String phone;
	
	private int managerId;
	private String manager;
	
	private String feastingDate;
	
	private OrderStatus orderStatus;
	
	private boolean reviewed;
	
	private long lastUpdated;
	
	public OrderDO(){}

	public OrderDO(int id, int customerId, OrderStatus orderStatus) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.orderStatus = orderStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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

	public String getCandidateDates() {
		return candidateDates;
	}

	public void setCandidateDates(String candidateDates) {
		this.candidateDates = candidateDates;
	}

	public int getRecommenderId() {
		return recommenderId;
	}

	public void setRecommenderId(int recommenderId) {
		this.recommenderId = recommenderId;
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

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getFeastingDate() {
		return feastingDate;
	}

	public void setFeastingDate(String feastingDate) {
		this.feastingDate = feastingDate;
	}

	public int getBanquetHallId() {
		return banquetHallId;
	}

	public void setBanquetHallId(int banquetHallId) {
		this.banquetHallId = banquetHallId;
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

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
