package com.iplay.dao.review;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.review.ReviewDO;

public interface ReviewDAO extends CrudRepository<ReviewDO, Integer>{
	
}
