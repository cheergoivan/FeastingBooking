package com.iplay.dao.hotel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.service.hotel.HotelService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelDAOTest {
	@SuppressWarnings("unused")
	@Autowired
	private HotelService hotelService;
	
	@SuppressWarnings("unused")
	@Autowired
	private HotelDAO hotelDAO;
	
	@SuppressWarnings("unused")
	@Autowired
	private BanquetHallDAO banquetHallDAO;
	
	@SuppressWarnings("unused")
	@Test
	public void test(){
		BanquetHallDO  banquetHall = new BanquetHallDO("banquet6", 20.0, 10, 100, 10000.0, 9.0
				,"","","","","",9);
		//banquetHall.setId(20);
		//banquetHallDAO.save(banquetHall);
	}
}
