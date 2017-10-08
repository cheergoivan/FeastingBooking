package com.iplay.service.order;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.component.util.DelimiterUtils;
import com.iplay.dao.order.OrderDAO;
import com.iplay.dto.ApiResponse;
import com.iplay.dto.ApiResponseMessage;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.entity.order.BHReservationDO;
import com.iplay.entity.order.OrderDO;
import com.iplay.entity.order.OrderStatus;
import com.iplay.entity.user.Role;
import com.iplay.service.storage.StorageService;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.service.user.UserService;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.order.PostReservationVO;
import com.iplay.web.exception.ResourceNotFoundException;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserService userService;

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private StorageService storageService;

	@Override
	public int addReservation(SimplifiedUser user, PostReservationVO vo) {
		int[] result = new int[] { -1 };
		userService.findSimplifiedUserByUsername(vo.getRecommender()).ifPresent(recommender -> {
			OrderDO order = new OrderDO();
			BHReservationDO bhReservationDO = new BHReservationDO();
			BeanUtils.copyProperties(vo, bhReservationDO);
			bhReservationDO.setBanquetHallDO(new BanquetHallDO(vo.getBanquetHallId()));
			bhReservationDO.setCustomerId(user.getUserId());
			bhReservationDO.setCustomer(user.getUsername());
			bhReservationDO.setRecommender(recommender.getUsername());
			bhReservationDO.setRecommenderId(recommender.getId());
			order.setBhReservation(bhReservationDO);
			order.setOrderStatus(OrderStatus.CONSULTING);
			order.setOrderTime(System.currentTimeMillis());
			OrderDO savedOrder = orderDAO.save(order);
			result[0] = savedOrder.getId();
		});
		return result[0];
	}

	@Override
	public ApiResponse<String> fillManager(int orderId, String manager) {
		boolean[] rs = {false};
		OrderDO order = findOrderById(orderId);
		userService.findSimplifiedUserByUsername(manager).filter(user -> user.getRole() == Role.MANAGER)
		.ifPresent(user->{
			order.setManager(user.getUsername());
			order.setManagerId(user.getId());
			orderDAO.save(order);
			rs[0] = true;
		});
		if(rs[0])
			return ApiResponse.SUCCESSFUL_RESPONSE_WITHOUT_MESSAGE;
		return ApiResponse.createFailApiResponse(ApiResponseMessage.MANAGER_NOT_FOUND);
	}

	@Override
	public boolean nextStatus(int orderId) {
		OrderDO order = findOrderById(orderId);
		OrderStatus next = order.getOrderStatus().next();
		if(next == null)
			return false;
		order.setOrderStatus(next);
		orderDAO.save(order);
		return true;
	}

	@Override
	public boolean fillAmountPaid(int orderId, double amountPaid) {
		OrderDO order = findOrderById(orderId);
		order.setAmountPaid(amountPaid);
		orderDAO.save(order);
		return true;
	}

	@Override
	public String[] uploadContract(int orderId, PostFilesVO vo) {
		OrderDO order = findOrderById(orderId);
		String[] files = uploadFiles(vo);
		order.setContract(DelimiterUtils.joinArray(files, DelimiterUtils.PICTURE_DELIMITER));
		orderDAO.save(order);
		return ResourcesUriBuilder.buildUris(files);
	}

	@Override
	public String[] uploadPayment(int orderId, PostFilesVO vo) {
		OrderDO order = findOrderById(orderId);
		String[] files = uploadFiles(vo);
		order.setPayment(DelimiterUtils.joinArray(files, DelimiterUtils.PICTURE_DELIMITER));
		orderDAO.save(order);
		return ResourcesUriBuilder.buildUris(files);
	}
	
	private String[] uploadFiles(PostFilesVO vo){
		MultipartFile[] files = vo.getFiles();
		String[] filenames = new String[files.length];
		for(int i=0;i<files.length;i++){
			String filename = storageService.store(files[i], UUIDNamingStrategy.generateUUID());
			filenames[i] = filename;
		}
		return filenames;
	}
	
	private OrderDO findOrderById(int orderId){
		OrderDO order = orderDAO.findOne(orderId);
		if(order == null)
			throw new ResourceNotFoundException("Order with id:"+orderId+" doesn't exist");
		return order;
	}

}
