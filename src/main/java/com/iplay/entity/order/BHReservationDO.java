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
@Table(name = "bh_reservation")
public class BHReservationDO {
	@Id
	@GeneratedValue
	private int id; 
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
	public BanquetHallDO getBanquetHallDO() {
		return banquetHallDO;
	}
	public void setBanquetHallDO(BanquetHallDO banquetHallDO) {
		this.banquetHallDO = banquetHallDO;
	}
}
