package com.iplay.service.order;

import com.iplay.dto.ApiResponse;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.order.PostReservationVO;

public interface OrderService {
	int addReservation(SimplifiedUser user, PostReservationVO vo);
	
	ApiResponse<String> fillManager(int orderId, String manager);
	
	boolean nextStatus(int orderId);
	
	boolean fillAmountPaid(int orderId, double amountPaid);
	
	String[] uploadContract(int orderId, PostFilesVO vo);
	
	String[] uploadPayment(int orderId, PostFilesVO vo);
	
}
