package com.iplay.dao.hotel;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.hotel.HotelRatingDO;

public interface HotelRatingDAO extends CrudRepository<HotelRatingDO, Integer>{
	
}
