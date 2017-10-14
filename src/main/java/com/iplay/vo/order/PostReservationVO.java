package com.iplay.vo.order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PostReservationVO {
	@NotNull
	private int banquetHallId;
	@NotNull
	private int tables;
	@NotEmpty
	@Size(max = 255)
	private String candidateDates;
	@Size(max = 255)
	private String recommender = "";
	@NotEmpty
	@Size(max = 255)
	private String contact;
	@NotEmpty
	@Size(max = 255)
	private String phone;
	
	public PostReservationVO(){}
	
	public PostReservationVO(int banquetHallId, int tables, String candidateDates, String recommender, String contact,
			String phone) {
		super();
		this.banquetHallId = banquetHallId;
		this.tables = tables;
		this.candidateDates = candidateDates;
		this.recommender = recommender;
		this.contact = contact;
		this.phone = phone;
	}
	
	public int getBanquetHallId() {
		return banquetHallId;
	}
	public void setBanquetHallId(int banquetHallId) {
		this.banquetHallId = banquetHallId;
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
}
