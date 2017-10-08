package com.iplay.vo.order;

public class PostReservationVO {
	private int banquetHallId;
	private int tables;
	private String candidateDates;
	private String recommender;
	private String contact;
	private String phone;
	
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
