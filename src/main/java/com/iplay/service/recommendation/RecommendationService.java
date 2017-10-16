package com.iplay.service.recommendation;

import java.util.List;

import com.iplay.dto.recommendation.RecommendationDTO;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.recommendation.RecommendationVO;

public interface RecommendationService {
	RecommendationDTO addRecommendation(RecommendationVO vo);
	
	boolean deleteRecommendations(EntityDeletionVO vo);
	
	boolean deleteRecommendation(int id);
	
	List<RecommendationDTO> listRecommendations();
}
