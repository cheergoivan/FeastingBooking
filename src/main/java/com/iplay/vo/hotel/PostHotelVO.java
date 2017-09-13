package com.iplay.vo.hotel;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class PostHotelVO {
	@NotBlank(message="hotel name can't be blank")
	private String name;
	private AddressVO address;
	@NotBlank(message="hotel description can't be blank")
	private String description;
	@NotBlank(message="hotel contact can't be blank")
	private String contact;
	@NotBlank(message="hotel telephone can't be blank")
	private String telephone;
	@NotBlank(message="hotel email can't be blank")
	private String email;
	@NotEmpty(message="Photos can not be empty")
	@Size(min=1,max=9,message="The quantity of photos must be in the range of 1 and 9")
	private MultipartFile[] files;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AddressVO getAddress() {
		return address;
	}
	public void setAddress(AddressVO address) {
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
	public MultipartFile[] getFiles() {
		return files;
	}
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
}
