package com.iplay.dao.hotel;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.hotel.FeastDO;

public interface FeastDAO extends CrudRepository<FeastDO, Integer>{
	@Transactional
	@Modifying
	@Query("update FeastDO h set h.pictures = concat(h.pictures ,?2) where h.id = ?1")
	int addPictures(int id, String pictures);
	
	/*
	@Transactional
	@Modifying
	@Query("update FeastDO h set h.deleted = ?2 where h.id = ?1")
	int updateDeleted(int id, boolean deleted);
	*/
}
