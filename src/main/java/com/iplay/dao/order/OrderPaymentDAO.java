package com.iplay.dao.order;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.common.EntityIdDO;
import com.iplay.entity.order.ApprovalStatus;
import com.iplay.entity.order.OrderDocIdApprovalStatusDO;
import com.iplay.entity.order.OrderPaymentDO;

public interface OrderPaymentDAO extends CrudRepository<OrderPaymentDO, Integer>{

	EntityIdDO findIdById(int id);
	
	@Transactional
	@Modifying
	@Query("update OrderPaymentDO h set h.approvalStatus = ?2 where h.id = ?1")
	int updateApprovalStatus(int id, ApprovalStatus status);
	
	OrderDocIdApprovalStatusDO findIdApprovalStatusById(int id);
}
