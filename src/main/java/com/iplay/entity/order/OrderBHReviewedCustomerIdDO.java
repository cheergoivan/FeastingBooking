package com.iplay.entity.order;

import com.iplay.entity.hotel.BanquetHallDO;

public interface OrderBHReviewedCustomerIdDO {
	BanquetHallDO getBanquetHallDO();
	
	boolean isReviewed();
	
	int getCustomerId();
}
