package com.iplay.entity.hotel;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "hotel")
public class HotelDO {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String address;
	private String description;
	private String contact;
	private String telephone;
	private String email;
	private int numOfPictures;
	@OneToMany
	@JoinColumn(name = "hotel_id")
	private List<BanquetHallDO> banquetHalls;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public int getNumOfPictures() {
		return numOfPictures;
	}

	public void setNumOfPictures(int numOfPictures) {
		this.numOfPictures = numOfPictures;
	}

	public List<BanquetHallDO> getBanquetHalls() {
		return banquetHalls;
	}

	public void setBanquetHalls(List<BanquetHallDO> banquetHalls) {
		this.banquetHalls = banquetHalls;
	}
}
