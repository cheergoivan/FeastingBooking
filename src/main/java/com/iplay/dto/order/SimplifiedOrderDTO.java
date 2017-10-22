package com.iplay.dto.order;

import java.util.List;

import com.iplay.entity.order.OrderStatus;
import com.iplay.service.order.RoleInOrder;

public class SimplifiedOrderDTO {
	private int id;
	private String hotel;
	private String banquetHall;
	private String date;
	private int tables;
	private OrderStatus orderStatus;
	private List<RoleInOrder> roleInOrder;
	
	public SimplifiedOrderDTO(){}
	
	public SimplifiedOrderDTO(int id, String hotel, String banquetHall, String date, int tables,
			OrderStatus orderStatus, List<RoleInOrder> roleInOrder) {
		super();
		this.id = id;
		this.hotel = hotel;
		this.banquetHall = banquetHall;
		this.date = date;
		this.tables = tables;
		this.orderStatus = orderStatus;
		this.roleInOrder = roleInOrder;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHotel() {
		return hotel;
	}
	public void setHotel(String hotel) {
		this.hotel = hotel;
	}
	public String getBanquetHall() {
		return banquetHall;
	}
	public void setBanquetHall(String banquetHall) {
		this.banquetHall = banquetHall;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTables() {
		return tables;
	}
	public void setTables(int tables) {
		this.tables = tables;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<RoleInOrder> getRoleInOrder() {
		return roleInOrder;
	}

	public void setRoleInOrder(List<RoleInOrder> roleInOrder) {
		this.roleInOrder = roleInOrder;
	}
}
