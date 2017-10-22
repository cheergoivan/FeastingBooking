package com.iplay.entity.order;

public interface SimplifiedOrderDO {
	int getId();

	String getBanquetHallName();
	
	String getHotelName();

	int getTables();
	
	long getFeastingDate();
	
	int getCustomerId();
	
	int getRecommenderId();
	
	int getManagerId();
	
	OrderStatus getOrderStatus();
}
