package com.iplay.service.recommendation;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.dao.recommendation.RecommendationDAO;
import com.iplay.dto.recommendation.RecommendationDTO;
import com.iplay.entity.recommendation.RecommendationDO;
import com.iplay.service.storage.StorageService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.recommendation.RecommendationVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class RecommendationServiceImpl implements RecommendationService{
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private RecommendationDAO recommendationDAO;

	@Override
	public int addRecommendation(RecommendationVO vo) {
		MultipartFile file = vo.getFile();
		String filename = storageService.store(file, UUIDNamingStrategy.generateUUID());
		RecommendationDO rec = new RecommendationDO(vo.getHotelId(), filename);
		return recommendationDAO.save(rec).getId();
	}

	@Override
	public boolean deleteRecommendations(EntityDeletionVO vo) {
		List<RecommendationDO> recs = new LinkedList<>();
		List<String> pictures = new LinkedList<>();
		recommendationDAO.findAll(vo.getIds()).forEach(rec->{
			recs.add(rec);
			pictures.add(rec.getPicture());
		});
		recommendationDAO.delete(recs);
		storageService.delete(pictures);
		return true;
	}

	@Override
	public boolean deleteRecommendation(int id) {
		RecommendationDO rec = recommendationDAO.findOne(id);
		if(rec!=null){
			storageService.delete(rec.getPicture());
			recommendationDAO.delete(rec);
		}
		return true;
	}

	@Override
	public List<RecommendationDTO> listRecommendations() {
		List<RecommendationDTO> recs = new LinkedList<>();
		recommendationDAO.findAll().forEach(rec->{
			recs.add(new RecommendationDTO(rec.getId(), rec.getHotelId(), ResourcesUriBuilder.buildUri(rec.getPicture())));
		});
		return recs;
	}
	
}
