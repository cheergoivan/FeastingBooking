package com.iplay.service.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.component.util.DelimiterUtils;
import com.iplay.dao.hotel.BanquetHallDAO;
import com.iplay.dao.order.OrderContractDAO;
import com.iplay.dao.order.OrderDAO;
import com.iplay.dao.order.OrderPaymentDAO;
import com.iplay.dto.order.SimplifiedOrderDTO;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.entity.order.ApprovalStatus;
import com.iplay.entity.order.OrderContractDO;
import com.iplay.entity.order.OrderDO;
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

	@Value("${feast-booking.date.format}")
	private String defaultDateFormat;

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
		order.setBanquetHallDO(banquetHall);
		order.setCustomerId(authenticatedUser.getUserId());
		order.setCustomer(authenticatedUser.getUsername());
		order.setOrderStatus(OrderStatus.CONSULTING);
		order.setOrderTime(System.currentTimeMillis());
		order.setOrderNumber(generateOrderNumber());
		OrderDO savedOrder = orderDAO.save(order);
		return savedOrder.getId();
	}

	@Override
	public boolean fillManager(SimplifiedUser authenticatedUser, int orderId, String manager) {
		boolean[] rs = { false };
		OrderDO order = findSimplifiedOrderById(orderId);
		if (order.getOrderStatus() != OrderStatus.CONSULTING || authenticatedUser.getUserId() != order.getCustomerId())
			throw new ResourceForbiddenException(
					"Order status must be " + OrderStatus.CONSULTING + " or you don't have authority!");
		userService.findSimplifiedUserByUsername(manager).filter(u -> u.getRole() == Role.MANAGER).ifPresent(u -> {
			orderDAO.updateManager(order.getId(), u.getId(), u.getUsername());
			rs[0] = true;
		});
		return rs[0];
	}

	@Override
	public boolean fillFeastingDate(SimplifiedUser authenticatedUser, int orderId, Date date) {
		OrderDO order = findSimplifiedOrderById(orderId);
		if (order.getOrderStatus() != OrderStatus.CONSULTING || authenticatedUser.getUserId() != order.getCustomerId())
			throw new ResourceForbiddenException(
					"Order status must be " + OrderStatus.CONSULTING + " or you don't have authority!");
		orderDAO.updateFeastingDate(orderId, date.getTime());
		return true;
	}

	@Override
	public OrderStatus moveToNextStatus(int orderId) {
		OrderDO order = findSimplifiedOrderById(orderId);
		OrderStatus next = order.getOrderStatus().next();
		if (next == null)
			throw new ResourceForbiddenException("Order in status CANCELED or DONE can't move to next!");
		orderDAO.updateStatus(orderId, next);
		return next;
	}

	@Override
	public OrderStatus updateStatus(int orderId, OrderStatus newOrderStatus) {
		findSimplifiedOrderById(orderId);
		orderDAO.updateStatus(orderId, newOrderStatus);
		return newOrderStatus;
	}

	@Override
	public boolean fillPayment(SimplifiedUser authenticatedUser, int orderId, PostPaymentVO postPaymentVO) {
		OrderDO order = findSimplifiedOrderById(orderId);
		if (order.getOrderStatus() != OrderStatus.RESERVED || authenticatedUser.getUserId() != order.getCustomerId())
			throw new ResourceForbiddenException(
					"Order status must be " + OrderStatus.RESERVED + " or you don't have authority!");
		OrderPaymentDO payment = orderPaymentDAO.findOne(order.getId());
		if (payment != null)
			storageService.delete(DelimiterUtils.split(payment.getPayment(), DelimiterUtils.PICTURE_DELIMITER));
		String[] files = uploadFiles(postPaymentVO.getFiles());
		orderPaymentDAO.save(new OrderPaymentDO(order.getId(), postPaymentVO.getAmountPaid(),
				DelimiterUtils.joinArray(files, DelimiterUtils.PICTURE_DELIMITER), System.currentTimeMillis(),
				ApprovalStatus.PENDING));
		return true;
	}

	@Override
	public boolean uploadContract(SimplifiedUser authenticatedUser, int orderId, PostFilesVO vo) {
		OrderDO order = findSimplifiedOrderById(orderId);
		if (order.getOrderStatus() != OrderStatus.CONSULTING || authenticatedUser.getUserId() != order.getCustomerId())
			throw new ResourceForbiddenException(
					"Order status must be " + OrderStatus.CONSULTING + " or you don't have authority!");
		OrderContractDO contract = orderContractDAO.findOne(order.getId());
		if (contract != null)
			storageService.delete(DelimiterUtils.split(contract.getContract(), DelimiterUtils.PICTURE_DELIMITER));
		String[] files = uploadFiles(vo.getFiles());
		orderContractDAO.save(
				new OrderContractDO(order.getId(), DelimiterUtils.joinArray(files, DelimiterUtils.PICTURE_DELIMITER),
						System.currentTimeMillis(), ApprovalStatus.PENDING));
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

	private OrderDO findSimplifiedOrderById(int orderId) {
		OrderIdCustomerIdStatusDO simplifiedOrder = orderDAO.findIdCustomerIdStatusById(orderId);
		if (simplifiedOrder == null)
			throw new ResourceNotFoundException("Order with id:" + orderId + " doesn't exist!");
		return new OrderDO(simplifiedOrder.getId(), simplifiedOrder.getCustomerId(), simplifiedOrder.getOrderStatus());
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
				BanquetHallDO bh = o.getBanquetHallDO();
				return new SimplifiedOrderDTO(o.getId(), bh.getName(), bh.getHotelDO().getName(),
						formatDate(o.getFeastingDate()), o.getTables(), o.getOrderStatus(),
						getRoleInOrder(currUser, o.getCustomerId(), o.getRecommenderId(), o.getManagerId()));
			});
		} catch (PropertyReferenceException e) {
			throw new InvalidRequestParametersException(e.getMessage());
		}
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

	private String formatDate(long date) {
		Date d = new Date(1292083200000L);
		return new SimpleDateFormat(defaultDateFormat).format(d);
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
