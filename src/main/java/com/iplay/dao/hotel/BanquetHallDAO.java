package com.iplay.dao.hotel;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.hotel.BanquetHallDO;

public interface BanquetHallDAO extends CrudRepository<BanquetHallDO, Integer>{
	
	//int saveBanquetHall(BanquetHallDO banquetHallDO, int hotelId);

}
