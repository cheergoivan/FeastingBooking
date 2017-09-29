package com.iplay.entity.hotel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.iplay.component.util.DelimiterUtils;

@Entity
@Table(name = "hotel")
public class HotelDO {
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String description;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private AddressDO address;
	private String contact;
	private String telephone;
	private String email;
	private String pictures;
	private int minimumTables;
	private int maximunTables;
	private double minimumPrice;
	private double maximumPrice;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_id")
	private List<BanquetHallDO> banquetHalls;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotel_id")
	private List<FeastDO> feasts;
	
	public HotelDO() {}

	public HotelDO(String name, String description, AddressDO address, String contact, String telephone, String email, String pictures) {
		super();
		this.name = name;
		this.description = description;
		this.address = address;
		this.contact = contact;
		this.telephone = telephone;
		this.email = email;
		this.pictures = pictures;
		this.banquetHalls = new ArrayList<>();
		this.feasts = new ArrayList<>();
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

	public AddressDO getAddress() {
		return address;
	}

	public void setAddress(AddressDO address) {
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

	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	public String[] getPicturesAsArray(){
		if(pictures.equals(""))
			return new String[0];
		return pictures.split(DelimiterUtils.PICTURE_DELIMITER);
	}

	public List<BanquetHallDO> getBanquetHalls() {
		return banquetHalls;
	}

	public void setBanquetHalls(List<BanquetHallDO> banquetHalls) {
		this.banquetHalls = banquetHalls;
	}

	public int getMinimumTables() {
		return minimumTables;
	}

	public void setMinimumTables(int minimumTables) {
		this.minimumTables = minimumTables;
	}

	public int getMaximunTables() {
		return maximunTables;
	}

	public void setMaximunTables(int maximunTables) {
		this.maximunTables = maximunTables;
	}

	public double getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public double getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public List<FeastDO> getFeasts() {
		return feasts;
	}

	public void setFeasts(List<FeastDO> feasts) {
		this.feasts = feasts;
	}
}
