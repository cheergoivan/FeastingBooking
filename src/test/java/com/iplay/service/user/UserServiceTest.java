package com.iplay.service.user;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.context.junit4.SpringRunner;

import com.iplay.dao.order.OrderDAO;
import com.iplay.entity.order.OrderStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderDAO orderDAO;
	
	private static final String[] USERS = {"ivan1","ivan2"};
	
	private static final String[] ADMINISTRATORS = {"admin"}; 
	
	private static final String[] MANAGERS = {"manager"}; 
	
	private static final String PASSWORD = "123456";
	
	@Test
	public void initializeUsers(){
		for(String user:USERS){
			if(!userService.isUsernameOccupied(user)){
				userService.createOrdinaryUser(user+"@FeastBooking.com", user, PASSWORD);
			}
		}
		for(String admin:ADMINISTRATORS){
			if(!userService.isUsernameOccupied(admin)){
				userService.createAdministrator(admin, PASSWORD);
			}
		}
		for(String manager:MANAGERS){
			if(!userService.isUsernameOccupied(manager)){
				userService.createManager(manager, PASSWORD);
			}
		}
	}
	
	@Test
	public void testFind(){
		Pageable p = new PageRequest(0, 10, new Sort(new Order(Direction.ASC, "id")));
		System.out.println(orderDAO.findByOrderStatusInAndCustomerIdOrRecommenderIdOrManagerId(Arrays.asList(OrderStatus.CONSULTING), 1, 1, 1, p).getTotalElements());
	}
}
