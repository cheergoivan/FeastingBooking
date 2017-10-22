package com.iplay.entity.order;

import com.iplay.entity.hotel.BanquetHallDO;

public interface SimplifiedOrderDO {
	int getId();

	BanquetHallDO getBanquetHallDO();

	int getTables();
	
	long getFeastingDate();
	
	int getCustomerId();
	
	int getRecommenderId();
	
	int getManagerId();
	
	OrderStatus getOrderStatus();
}
