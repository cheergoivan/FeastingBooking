package com.iplay.dao.order;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.order.OrderDO;
import com.iplay.entity.order.OrderStatus;

public interface OrderDAO extends CrudRepository<OrderDO, Integer>{
	
	@Transactional
	@Modifying
	@Query("update OrderDO h set h.managerId = ?2, h.manager = ?3 where h.id = ?1")
	int updateManager(int id, int managerId, String manager);
	
	@Transactional
	@Modifying
	@Query("update OrderDO h set h.orderStatus = ?2 where h.id = ?1")
	int updateStatus(int id, OrderStatus status);
	
	Page<OrderDO> findByCustomerId(int customerId, Pageable pageable);
	
	Page<OrderDO> findByRecommenderId(int recommenderId, Pageable pageable);
	
	Page<OrderDO> findByManagerId(int managerId, Pageable pageable);
}
