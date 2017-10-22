package com.iplay.entity.order;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
	private long orderTime;
	
	private long orderNumber;

	private int banquetHallId;
	private String banquetHallName;
	private int hotelId;
	private String hotelName;
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
	
	private long feastingDate;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private OrderContractDO orderContractDO;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private OrderPaymentDO orderPaymentDO;
	
	private OrderStatus orderStatus;
	
	private boolean reviewed;
	
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

	public OrderContractDO getOrderContractDO() {
		return orderContractDO;
	}

	public void setOrderContractDO(OrderContractDO orderContractDO) {
		this.orderContractDO = orderContractDO;
	}

	public OrderPaymentDO getOrderPaymentDO() {
		return orderPaymentDO;
	}

	public void setOrderPaymentDO(OrderPaymentDO orderPaymentDO) {
		this.orderPaymentDO = orderPaymentDO;
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

	public long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(long orderTime) {
		this.orderTime = orderTime;
	}

	public long getFeastingDate() {
		return feastingDate;
	}

	public void setFeastingDate(long feastingDate) {
		this.feastingDate = feastingDate;
	}

	public int getBanquetHallId() {
		return banquetHallId;
	}

	public void setBanquetHallId(int banquetHallId) {
		this.banquetHallId = banquetHallId;
	}

	public String getBanquetHallName() {
		return banquetHallName;
	}

	public void setBanquetHallName(String banquetHallName) {
		this.banquetHallName = banquetHallName;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
}
