package com.iplay.dao.hotel.rating;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.hotel.rating.HotelRatingRecordDO;

public interface HotelRatingRecordDAO extends CrudRepository<HotelRatingRecordDO, Integer> {
	
	HotelRatingRecordDO findByUserIdAndHotelId(int userId, int hotelId);

}
