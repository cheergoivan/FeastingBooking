package com.iplay.service.order;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.component.util.DelimiterUtils;
import com.iplay.dao.hotel.BanquetHallDAO;
import com.iplay.dao.order.OrderContractDAO;
import com.iplay.dao.order.OrderDAO;
import com.iplay.dao.order.OrderPaymentDAO;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.entity.order.ApprovalStatus;
import com.iplay.entity.order.OrderContractDO;
import com.iplay.entity.order.OrderDO;
import com.iplay.entity.order.OrderPaymentDO;
import com.iplay.entity.order.OrderStatus;
import com.iplay.entity.user.Role;
import com.iplay.service.exception.ResourceForbiddenException;
import com.iplay.service.exception.ResourceNotFoundException;
import com.iplay.service.storage.StorageService;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.service.user.UserService;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.order.PostPaymentVO;
import com.iplay.vo.order.PostReservationVO;
import com.iplay.component.util.StringUtils;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserService userService;

	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private OrderContractDAO orderContractDAO;
	
	@Autowired
	private OrderPaymentDAO orderPaymentDAO;
	
	@Autowired
	private BanquetHallDAO banquetHallDAO;
	
	@Autowired
	private StorageService storageService;

	@Override
	@Transactional
	public int addReservation(SimplifiedUser authenticatedUser, PostReservationVO vo) {
		BanquetHallDO banquetHall = banquetHallDAO.findOne(vo.getBanquetHallId());
		if(banquetHall == null)
			throw new ResourceNotFoundException("Banquet hall with id: "+vo.getBanquetHallId()+" doesn't exist");
		OrderDO order = new OrderDO();
		if(!StringUtils.isNullOrEmpty(vo.getRecommender())){
			boolean[] recommenderExists = {false};
			userService.findSimplifiedUserByUsername(vo.getRecommender()).filter(recommender -> recommender.getRole() !=Role.ADMIN)
			.ifPresent(recommender -> {
				order.setRecommender(recommender.getUsername());
				order.setRecommenderId(recommender.getId());
				recommenderExists[0] = true;
			});
			if(!recommenderExists[0])
				return -1;
		}
		BeanUtils.copyProperties(vo, order);
		order.setBanquetHallDO(banquetHall);
		order.setCustomerId(authenticatedUser.getUserId());
		order.setCustomer(authenticatedUser.getUsername());
		order.setOrderStatus(OrderStatus.CONSULTING);
		order.setOrderTime(System.currentTimeMillis());
		OrderDO savedOrder = orderDAO.save(order);
		return savedOrder.getId();
	}

	@Override
	public boolean fillManager(SimplifiedUser authenticatedUser, int orderId, String manager) {
		boolean[] rs = {false};
		OrderDO order = findOrderById(orderId);
		if(order.getOrderStatus()!=OrderStatus.CONSULTING||authenticatedUser.getUserId()!=order.getCustomerId())
			throw new ResourceForbiddenException("Order status must be "+OrderStatus.CONSULTING+" or you don't have authority!");
		userService.findSimplifiedUserByUsername(manager).filter(u -> u.getRole() == Role.MANAGER)
		.ifPresent(u->{
			orderDAO.updateManager(order.getId(), u.getId(), u.getUsername());
			rs[0] = true;
		});
		return rs[0];
	}

	@Override
	public OrderStatus moveToNextStatus(int orderId) {
		OrderDO order = findOrderById(orderId);
		OrderStatus next = order.getOrderStatus().next();
		if(next == null)
			throw new ResourceForbiddenException("Order in status CANCELED or DONE can't move to next!");
		orderDAO.updateStatus(orderId, next);
		return next;
	}
	
	@Override
	public OrderStatus updateStatus(int orderId, OrderStatus newOrderStatus) {
		findOrderById(orderId);
		orderDAO.updateStatus(orderId, newOrderStatus);
		return newOrderStatus;
	}
	
	@Override
	public boolean fillPayment(SimplifiedUser authenticatedUser, int orderId, PostPaymentVO postPaymentVO) {
		OrderDO order = findOrderById(orderId);
		if(order.getOrderStatus()!=OrderStatus.RESERVED||authenticatedUser.getUserId()!=order.getCustomerId())
			throw new ResourceForbiddenException("Order status must be "+OrderStatus.RESERVED+" or you don't have authority!");
		OrderPaymentDO payment = order.getOrderPaymentDO();
		if(payment!=null)
			storageService.delete(DelimiterUtils.split(payment.getPayment(), DelimiterUtils.PICTURE_DELIMITER));
		String[] files = uploadFiles(postPaymentVO.getFiles());
		orderPaymentDAO.save(new OrderPaymentDO(order.getId(), postPaymentVO.getAmountPaid(), DelimiterUtils.joinArray(files, DelimiterUtils.PICTURE_DELIMITER), 
				System.currentTimeMillis(), ApprovalStatus.PENDING));
		return true;
	}

	@Override
	public boolean uploadContract(SimplifiedUser authenticatedUser, int orderId, PostFilesVO vo) {
		OrderDO order = findOrderById(orderId);
		if(order.getOrderStatus()!=OrderStatus.CONSULTING||authenticatedUser.getUserId()!=order.getCustomerId())
			throw new ResourceForbiddenException("Order status must be "+OrderStatus.CONSULTING+" or you don't have authority!");
		OrderContractDO contract = order.getOrderContractDO();
		if(contract!=null)
			storageService.delete(DelimiterUtils.split(contract.getContract(),DelimiterUtils.PICTURE_DELIMITER));
		String[] files = uploadFiles(vo.getFiles());
		orderContractDAO.save(new OrderContractDO(order.getId(), DelimiterUtils.joinArray(files, DelimiterUtils.PICTURE_DELIMITER), 
				System.currentTimeMillis(), ApprovalStatus.PENDING));
		return true;
	}
	
	private String[] uploadFiles(MultipartFile[] files){
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
