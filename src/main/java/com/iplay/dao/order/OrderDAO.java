package com.iplay.dao.order;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.order.OrderBHReviewedCustomerIdDO;
import com.iplay.entity.order.OrderDO;
import com.iplay.entity.order.OrderIdCustomerIdStatusDO;
import com.iplay.entity.order.OrderStatus;
import com.iplay.entity.order.SimplifiedOrderDO;

public interface OrderDAO extends CrudRepository<OrderDO, Integer>, JpaSpecificationExecutor<OrderDO> {
	
	@Transactional
	@Modifying
	@Query("update OrderDO h set h.managerId = ?2, h.manager = ?3 where h.id = ?1")
	int updateManager(int id, int managerId, String manager);
	
	@Transactional
	@Modifying
	@Query("update OrderDO h set h.feastingDate = ?2 where h.id = ?1")
	int updateFeastingDate(int id, long date);
	
	@Transactional
	@Modifying
	@Query("update OrderDO h set h.orderStatus = ?2 where h.id = ?1")
	int updateStatus(int id, OrderStatus status);
	
	@Transactional
	@Modifying
	@Query("update OrderDO h set h.reviewed = ?2 where h.id = ?1")
	int updateReviewed(int id, boolean reviewed);
	
	OrderBHReviewedCustomerIdDO findBHReviewedCustomerIdById(int id);
	
	OrderIdCustomerIdStatusDO findIdCustomerIdStatusById(int id);
	
	Page<SimplifiedOrderDO> findByOrderStatusInAndCustomerIdOrRecommenderIdOrManagerId(Collection<OrderStatus> orderStatus,int customerId, int recommenderId, 
			int managerId, Pageable pageable);
	
	Page<OrderDO> findByCustomerId(int customerId, Pageable pageable);
	
	Page<OrderDO> findByRecommenderId(int recommenderId, Pageable pageable);
	
	Page<OrderDO> findByManagerId(int managerId, Pageable pageable);
}
