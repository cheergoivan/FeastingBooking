package com.iplay.entity.order.specification;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.iplay.entity.order.OrderDO;
import com.iplay.entity.order.OrderStatus;
import com.iplay.service.order.RoleInOrder;

public class OrderQuerySpecification implements Specification<OrderDO>{

	private int userId;
	
	private List<OrderStatus> orderStatus;
	
	private List<RoleInOrder> roles;
	
	public OrderQuerySpecification(){}
	
	public OrderQuerySpecification(int querier, List<OrderStatus> orderStatus, List<RoleInOrder> roles){
		this.userId = querier;
		this.orderStatus = orderStatus;
		this.roles = roles;
	}
	
	@Override
	public Predicate toPredicate(Root<OrderDO> root, CriteriaQuery<?> query, 
			CriteriaBuilder cb) {
		Predicate[] rolePredicate = new Predicate[roles.size()];
		for(int i=0;i<roles.size();i++){
			rolePredicate[i] = cb.equal(root.get(roles.get(i).toString().toLowerCase()+"Id"), 
					userId);
		}
		return cb.and(cb.or(rolePredicate), root.get("orderStatus").in(orderStatus));
	}

}
