package com.iplay.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.iplay.component.dateFormat.FeastBookingDateFormatter;
import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.component.util.DelimiterUtils;
import com.iplay.dao.hotel.BanquetHallDAO;
import com.iplay.dao.order.OrderContractDAO;
import com.iplay.dao.order.OrderDAO;
import com.iplay.dao.order.OrderPaymentDAO;
import com.iplay.dto.order.OrderDTO;
import com.iplay.dto.order.SimplifiedOrderDTO;
import com.iplay.entity.common.EntityIdDO;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.entity.hotel.HotelDO;
import com.iplay.entity.order.ApprovalStatus;
import com.iplay.entity.order.ApprovalStatus.ModifiableApprovalStatus;
import com.iplay.entity.order.OrderContractDO;
import com.iplay.entity.order.OrderDO;
import com.iplay.entity.order.OrderDocIdApprovalStatusDO;
import com.iplay.entity.order.OrderIdCustomerIdStatusDO;
import com.iplay.entity.order.OrderPaymentDO;
import com.iplay.entity.order.OrderStatus;
import com.iplay.entity.order.SimplifiedOrderDO;
import com.iplay.entity.user.Role;
import com.iplay.service.exception.InvalidRequestParametersException;
import com.iplay.service.exception.ResourceForbiddenException;
import com.iplay.service.exception.ResourceNotFoundException;
import com.iplay.service.storage.StorageService;
import com.iplay.service.user.SimplifiedUser;
import com.iplay.service.user.UserService;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.order.OrderStatusVO;
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

	@Autowired
	private IdGenerator idGenerator;
	
	@Autowired
	private FeastBookingDateFormatter dateFormatter;

	@Override
	@Transactional
	public int addReservation(SimplifiedUser authenticatedUser, PostReservationVO vo) {
		BanquetHallDO banquetHall = banquetHallDAO.findOne(vo.getBanquetHallId());
		if (banquetHall == null)
			throw new ResourceNotFoundException("Banquet hall with id: " + vo.getBanquetHallId() + " doesn't exist");
		OrderDO order = new OrderDO();
		if (!StringUtils.isNullOrEmpty(vo.getRecommender())) {
			boolean[] recommenderExists = { false };
			userService.findSimplifiedUserByUsername(vo.getRecommender())
					.filter(recommender -> recommender.getRole() != Role.ADMIN).ifPresent(recommender -> {
						order.setRecommender(recommender.getUsername());
						order.setRecommenderId(recommender.getId());
						recommenderExists[0] = true;
					});
			if (!recommenderExists[0])
				return -1;
		}
		BeanUtils.copyProperties(vo, order);
		order.setBanquetHallId(banquetHall.getId());
		order.setBanquetHall(banquetHall.getName());
		HotelDO hotel = banquetHall.getHotelDO();
		order.setHotelId(hotel.getId());
		order.setHotel(hotel.getName());
		order.setCustomerId(authenticatedUser.getUserId());
		order.setCustomer(authenticatedUser.getUsername());
		order.setOrderStatus(OrderStatus.CONSULTING);
		order.setCreationTime(System.currentTimeMillis());
		order.setOrderNumber(generateOrderNumber());
		order.setLastUpdated(order.getCreationTime());
		OrderDO savedOrder = orderDAO.save(order);
		return savedOrder.getId();
	}

	@Override
	public boolean fillManager(SimplifiedUser authenticatedUser, int orderId, String manager) {
		boolean[] rs = { false };
		OrderDO order = checkExistenceOfOrder(orderId);
		if (order.getOrderStatus() != OrderStatus.CONSULTING || authenticatedUser.getUserId() != order.getCustomerId())
			throw new ResourceForbiddenException(
					"Order status must be " + OrderStatus.CONSULTING + " or you don't have authority!");
		userService.findSimplifiedUserByUsername(manager).filter(u -> u.getRole() == Role.MANAGER).ifPresent(u -> {
			orderDAO.updateManager(order.getId(), u.getId(), u.getUsername(), System.currentTimeMillis());
			rs[0] = true;
		});
		return rs[0];
	}

	@Override
	public boolean fillFeastingDate(SimplifiedUser authenticatedUser, int orderId, String date) {
		OrderDO order = checkExistenceOfOrder(orderId);
		if (order.getOrderStatus() != OrderStatus.CONSULTING || authenticatedUser.getUserId() != order.getCustomerId())
			throw new ResourceForbiddenException(
					"Order status must be " + OrderStatus.CONSULTING + " or you don't have authority!");
		orderDAO.updateFeastingDate(orderId, date, System.currentTimeMillis());
		return true;
	}

	@Override
	public OrderStatus moveToNextStatus(int orderId) {
		OrderDO order = checkExistenceOfOrder(orderId);
		OrderStatus next = order.getOrderStatus().next();
		if (next == null)
			throw new ResourceForbiddenException("Order in status CANCELED or DONE can't move to next!");
		orderDAO.updateStatus(orderId, next);
		return next;
	}

	@Override
	public OrderStatus updateStatus(int orderId, OrderStatus newOrderStatus) {
		checkExistenceOfOrder(orderId);
		orderDAO.updateStatus(orderId, newOrderStatus);
		return newOrderStatus;
	}

	@Override
	@Transactional
	public boolean fillPayment(SimplifiedUser authenticatedUser, int orderId, PostPaymentVO postPaymentVO) {
		OrderDO order = checkExistenceOfOrder(orderId);
		if (order.getOrderStatus() != OrderStatus.RESERVED || authenticatedUser.getUserId() != order.getCustomerId())
			throw new ResourceForbiddenException(
					"Order status must be " + OrderStatus.RESERVED + " or you don't have authority!");
		OrderPaymentDO payment = orderPaymentDAO.findOne(order.getId());
		if(payment!=null && payment.getApprovalStatus()!=ApprovalStatus.PENDING && payment.getApprovalStatus()!=ApprovalStatus.DECLINED)
			throw new ResourceForbiddenException(
					"Order payment must be in " + ApprovalStatus.PENDING +" status or "+ApprovalStatus.DECLINED+ " status!");
		if (payment != null)
			storageService.delete(DelimiterUtils.split(payment.getPayment(), DelimiterUtils.GLOBAL_DEFAULT_DELIMITER));
		String[] files = uploadFiles(postPaymentVO.getFiles());
		orderPaymentDAO.save(new OrderPaymentDO(order.getId(), postPaymentVO.getAmountPaid(),
				DelimiterUtils.joinArray(files, DelimiterUtils.GLOBAL_DEFAULT_DELIMITER), System.currentTimeMillis(),
				ApprovalStatus.PENDING));
		orderDAO.updateOrder(order.getId(), System.currentTimeMillis());
		return true;
	}

	@Override
	@Transactional
	public boolean uploadContract(SimplifiedUser authenticatedUser, int orderId, PostFilesVO vo) {
		OrderDO order = checkExistenceOfOrder(orderId);
		if (order.getOrderStatus() != OrderStatus.CONSULTING || authenticatedUser.getUserId() != order.getCustomerId())
			throw new ResourceForbiddenException(
					"Order status must be " + OrderStatus.CONSULTING + " or you don't have authority!");
		OrderContractDO contract = orderContractDAO.findOne(order.getId());
		if(contract!=null && contract.getApprovalStatus()!=ApprovalStatus.PENDING && contract.getApprovalStatus()!=ApprovalStatus.DECLINED)
			throw new ResourceForbiddenException(
					"Order contract must be in " + ApprovalStatus.PENDING +" status or "+ApprovalStatus.DECLINED+ " status!");
		if (contract != null)
			storageService.delete(DelimiterUtils.split(contract.getContract(), DelimiterUtils.GLOBAL_DEFAULT_DELIMITER));
		String[] files = uploadFiles(vo.getFiles());
		orderContractDAO.save(
				new OrderContractDO(order.getId(), DelimiterUtils.joinArray(files, DelimiterUtils.GLOBAL_DEFAULT_DELIMITER),
						System.currentTimeMillis(), ApprovalStatus.PENDING));
		orderDAO.updateOrder(order.getId(), System.currentTimeMillis());
		return true;
	}

	private String[] uploadFiles(MultipartFile[] files) {
		String[] filenames = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			String filename = storageService.store(files[i], UUIDNamingStrategy.generateUUID());
			filenames[i] = filename;
		}
		return filenames;
	}

	private OrderDO checkExistenceOfOrder(int orderId) {
		OrderIdCustomerIdStatusDO simplifiedOrder = orderDAO.findIdCustomerIdStatusById(orderId);
		if (simplifiedOrder == null)
			throw new ResourceNotFoundException("Order with id: " + orderId + " doesn't exist!");
		return new OrderDO(simplifiedOrder.getId(), simplifiedOrder.getCustomerId(), simplifiedOrder.getOrderStatus());
	}
	
	private OrderDocIdApprovalStatusDO checkExistenceOfOrderContract(int orderId) {
		OrderDocIdApprovalStatusDO simplifiedOrderContract = orderContractDAO.findIdApprovalStatusById(orderId);
		if(simplifiedOrderContract == null)
			throw new ResourceNotFoundException("Order with id: " + orderId + " doesn't have contract information yet!");
		return simplifiedOrderContract;
	}
	
	@SuppressWarnings("unused")
	private EntityIdDO checkExistenceOfOrderPayment(int orderId) {
		EntityIdDO entityIdDO = orderPaymentDAO.findIdById(orderId);
		if(entityIdDO == null)
			throw new ResourceNotFoundException("Order with id: " + orderId + " doesn't have payment information yet!");
		return entityIdDO;
	}

	@Override
	public long generateOrderNumber() {
		return idGenerator.generateId().longValue();
	}

	@Override
	public Page<SimplifiedOrderDTO> listOrders(SimplifiedUser authenticatedUser, OrderStatusVO vo, Pageable pageable) {
		int currUser = authenticatedUser.getUserId();
		try {

			Page<SimplifiedOrderDO> page = orderDAO.findByOrderStatusInAndCustomerIdOrRecommenderIdOrManagerId(
					getOrderStatusCollection(vo), currUser, currUser, currUser, pageable);
			return page.map(o -> {
				return new SimplifiedOrderDTO(o.getId(), o.getHotel(), o.getBanquetHall(), 
						dateFormatter.toDefaultFormattedDate(o.getFeastingDate()), o.getTables(), o.getOrderStatus(),
						getRoleInOrder(currUser, o.getCustomerId(), o.getRecommenderId(), o.getManagerId()));
			});
		} catch (PropertyReferenceException e) {
			throw new InvalidRequestParametersException(e.getMessage());
		}
	}
	
	@Override
	public Optional<OrderDTO> findOrderById(SimplifiedUser authenticatedUser, int id) {
		OrderDTO orderDTO  = null;
		OrderDO orderDO = orderDAO.findOne(id);
		if(orderDO!=null) {
			if(orderDO.getCustomerId()!=authenticatedUser.getUserId() && orderDO.getManagerId()!=authenticatedUser.getUserId())
				throw new ResourceForbiddenException("No authority!");
			orderDTO = new OrderDTO();
			BeanUtils.copyProperties(orderDO, orderDTO);
			orderDTO.setCreationTime(dateFormatter.toOrderCreationTime(orderDO.getCreationTime()));
			orderDTO.setLastUpdated(dateFormatter.toOrderLastUpdated(orderDO.getLastUpdated()));
			orderDTO.setCandidateDates(DelimiterUtils.split(orderDO.getCandidateDates(), DelimiterUtils.GLOBAL_DEFAULT_DELIMITER));
		}
		return Optional.ofNullable(orderDTO);
	}

	@Override
	public boolean updateOrderContractApprovalStatus(int orderId, ModifiableApprovalStatus approvalStatus) {
		OrderDocIdApprovalStatusDO simplifiedOrderContract = checkExistenceOfOrderContract(orderId);
		if(simplifiedOrderContract.getApprovalStatus() == ApprovalStatus.PENDING)
			orderContractDAO.updateApprovalStatus(orderId, approvalStatus.toApprovalStatus());
		else
			throw new ResourceForbiddenException(
					"Order contract must be in " + ApprovalStatus.PENDING +" status!");
		return false;
	}

	@Override
	public boolean updateOrderPaymentApprovalStatus(int orderId, ModifiableApprovalStatus approvalStatus) {
		return false;
	}

	private List<RoleInOrder> getRoleInOrder(int userId, int customerId, int recommenderId, int managerId) {
		List<RoleInOrder> roles = new ArrayList<>();
		if (userId == customerId)
			roles.add(RoleInOrder.CUSTOMER);
		if (userId == recommenderId)
			roles.add(RoleInOrder.RECOMMENDER);
		if (userId == managerId)
			roles.add(RoleInOrder.MANAGER);
		return roles;
	}

	private List<OrderStatus> getOrderStatusCollection(OrderStatusVO vo){
		switch(vo){
		case FINISHED:
			return Arrays.asList(OrderStatus.DONE);
		case UNFINISHED:
			return OrderStatus.unfinishedStatus();
		}
		return new ArrayList<>();
	}
}
