package com.iplay.dao.recommendation;

import org.springframework.data.repository.CrudRepository;

import com.iplay.entity.recommendation.RecommendationDO;

public interface RecommendationDAO extends CrudRepository<RecommendationDO,Integer>{

}
