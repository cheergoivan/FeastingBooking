package com.iplay.entity.order;

public interface SimplifiedOrderDO {
	int getId();

	String getBanquetHall();
	
	String getHotel();

	int getTables();
	
	long getFeastingDate();
	
	int getCustomerId();
	
	int getRecommenderId();
	
	int getManagerId();
	
	OrderStatus getOrderStatus();
}
