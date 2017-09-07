package com.iplay.dao.hotel;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.hotel.HotelDO;

public interface HotelDAO extends CrudRepository<HotelDO,Integer>{
	
}
