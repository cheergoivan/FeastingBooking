package com.iplay.dao.order;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.common.EntityIdDO;
import com.iplay.entity.order.OrderPaymentDO;

public interface OrderPaymentDAO extends CrudRepository<OrderPaymentDO, Integer>{

	EntityIdDO findIdById(int id);
	
}
