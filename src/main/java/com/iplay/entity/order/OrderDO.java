package com.iplay.entity.order;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.iplay.entity.hotel.BanquetHallDO;

@Entity
@Table(name = "feast_order")
public class OrderDO {
	@Id
	@GeneratedValue
	private int id;
	private long orderTime;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bh_id")
	private BanquetHallDO banquetHallDO;
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private OrderContractDO orderContractDO;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private OrderPaymentDO orderPaymentDO;
	
	private OrderStatus orderStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(long orderTime) {
		this.orderTime = orderTime;
	}

	public BanquetHallDO getBanquetHallDO() {
		return banquetHallDO;
	}

	public void setBanquetHallDO(BanquetHallDO banquetHallDO) {
		this.banquetHallDO = banquetHallDO;
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
}
