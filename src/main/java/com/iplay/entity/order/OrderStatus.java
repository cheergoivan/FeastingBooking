package com.iplay.entity.order;

public enum OrderStatus {
	CANCELED, CONSULTING, NEGOTIATING, RESERVED, FEASTING, CASHBACK, DONE;
	
	public OrderStatus next(){
		OrderStatus[] status = OrderStatus.values();
		if(this.ordinal() == status.length-1||this == OrderStatus.CANCELED)
			return null;
		return status[this.ordinal()+1];
	}
}
