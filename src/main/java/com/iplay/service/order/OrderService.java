package com.iplay.service.order;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iplay.dto.order.SimplifiedOrderDTO;
import com.iplay.entity.order.OrderStatus;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.order.OrderStatusVO;
import com.iplay.vo.order.PostPaymentVO;
import com.iplay.vo.order.PostReservationVO;

public interface OrderService {
	/**
	 * 
	 * @param user
	 * @param vo
	 * @return the created order id
	 */
	int addReservation(SimplifiedUser authenticatedUser, PostReservationVO vo);
	
	boolean fillManager(SimplifiedUser authenticatedUser, int orderId, String manager);
	
	boolean fillFeastingDate(SimplifiedUser authenticatedUser, int orderId, Date date);
	
	OrderStatus moveToNextStatus(int orderId);
	
	OrderStatus updateStatus(int orderId, OrderStatus newOrderStatus);
	
	boolean fillPayment(SimplifiedUser authenticatedUser, int orderId, PostPaymentVO postPaymentVO);
	
	boolean uploadContract(SimplifiedUser authenticatedUser, int orderId, PostFilesVO vo);
	
	long generateOrderNumber();
	
	Page<SimplifiedOrderDTO> listOrders(SimplifiedUser authenticatedUser, OrderStatusVO vo , Pageable pageable);
}
