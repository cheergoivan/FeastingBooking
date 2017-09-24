package com.iplay.dto.hotel;

public class SimplifiedHotelAdminDTO {

	private int id;
	private String name;
	private String contact;
	private String telephone;
	private String email;
	private String address;
	public SimplifiedHotelAdminDTO() {}
	public SimplifiedHotelAdminDTO(int id, String name, String contact, String telephone, String email,
			String address) {
		super();
		this.id = id;
		this.name = name;
		this.contact = contact;
		this.telephone = telephone;
		this.email = email;
		this.address = address;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
