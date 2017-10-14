package com.iplay.service.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iplay.service.user.SimplifiedUser;
import com.iplay.vo.order.PostReservationVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
	@Autowired
	private OrderService orderService;
	
	@Test
	public void testInsert(){
		String name = "";
		for(int i=0;i<255;i++){
			name+="你";
		}
		SimplifiedUser customer = new SimplifiedUser(1, name);
		PostReservationVO vo = new PostReservationVO(1, 100, "2017-10-21", "ivan2", "simon", "15850781286");
		orderService.addReservation(customer, vo);
	}
}
