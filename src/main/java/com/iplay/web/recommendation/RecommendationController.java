package com.iplay.web.recommendation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.dto.recommendation.RecommendationDTO;
import com.iplay.service.recommendation.RecommendationService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.recommendation.RecommendationVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
	
	@Autowired
	private RecommendationService recommendationService;
	
	@ApiOperation(notes="管理员添加一个推荐, 返回推荐id",value="")
    @PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int addRecommendations(@ApiParam("推荐酒店实体")@Valid RecommendationVO vo){
		return recommendationService.addRecommendation(vo);
	}
	
	@ApiOperation(notes="获取推荐列表",value="")
    @GetMapping
    public List<RecommendationDTO> getRecommendations(){
		return recommendationService.listRecommendations();
	}
	
	@ApiOperation(notes = "管理員批量删除推荐，返回boolean", value = "")
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteRecs(@Valid@RequestBody@ApiParam("推荐id集合") EntityDeletionVO vo){
		return recommendationService.deleteRecommendations(vo);
	}
	
	@ApiOperation(notes="管理员删除一个推荐，返回boolean",value="")
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteRec(@ApiParam("推荐id")@PathVariable int id){
		return recommendationService.deleteRecommendation(id);
	}
    
}
