package com.iplay.dao.hotel;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.iplay.entity.hotel.HotelDO;

public interface HotelDAO extends PagingAndSortingRepository<HotelDO,Integer>{
	
	@Transactional
	@Modifying
	@Query("update HotelDO h set h.pictures = concat(h.pictures ,?2) where h.id = ?1")
	int addPictures(int id, String pictures);

}
