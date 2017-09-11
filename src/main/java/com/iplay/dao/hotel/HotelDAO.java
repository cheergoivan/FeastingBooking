package com.iplay.dao.hotel;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.iplay.entity.hotel.HotelDO;

public interface HotelDAO extends PagingAndSortingRepository<HotelDO,Integer>{
	
}
