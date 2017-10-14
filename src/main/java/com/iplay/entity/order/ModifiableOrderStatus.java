package com.iplay.entity.order;

public enum ModifiableOrderStatus {
	CANCELED;
	
	public OrderStatus toOrderStatus(){
		return OrderStatus.valueOf(this.toString());
	}
}
