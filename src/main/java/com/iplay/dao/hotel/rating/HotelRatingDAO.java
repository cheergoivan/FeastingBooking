package com.iplay.dao.hotel.rating;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.hotel.rating.HotelRatingDO;

public interface HotelRatingDAO extends CrudRepository<HotelRatingDO, Integer>{
	@Transactional
	@Modifying
	@Query("update HotelRatingDO h set h.totalScore = h.totalScore + ?1, h.times = h.times + 1 where h.id = ?2")
	int updateScore(double score, int id);
}
