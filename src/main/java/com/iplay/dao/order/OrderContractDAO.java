package com.iplay.dao.order;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.common.EntityIdDO;
import com.iplay.entity.order.OrderContractDO;

public interface OrderContractDAO extends CrudRepository<OrderContractDO, Integer> {
	
	EntityIdDO findIdById(int id);

}
