package com.iplay.dao.order;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.order.OrderDO;

public interface OrderDAO extends CrudRepository<OrderDO, Integer>{
	
}