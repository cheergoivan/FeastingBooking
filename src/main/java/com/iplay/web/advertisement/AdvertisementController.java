package com.iplay.web.advertisement;

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

import com.iplay.dto.advertisement.AdvertisementDTO;
import com.iplay.service.advertisement.AdvertisementService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.PostFilesVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {
	
	@Autowired
	private AdvertisementService advertisementService;
	
	@ApiOperation(notes="管理员添加广告",value="")
    @PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<AdvertisementDTO> addAdvertisements(@ApiParam("MultipartFile[] files")@Valid 
			PostFilesVO pictures){
		return advertisementService.addAdvertisements(pictures);
	}
	
	@ApiOperation(notes="获取广告列表",value="")
    @GetMapping
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
    public List<AdvertisementDTO> getAdvertisements(){
		return advertisementService.listAdvs();
	}
	
	
	@ApiOperation(notes = "管理員批量删除广告，返回boolean", value = "")
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteAdvs(@Valid@RequestBody@ApiParam("广告id集合") EntityDeletionVO entityDeletionVO){
		return advertisementService.deleteAdvs(entityDeletionVO);
	}
	
	@ApiOperation(notes="管理员删除一个广告，返回boolean",value="")
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteAdv(@ApiParam("广告id")@PathVariable int id){
		return advertisementService.deleteAdv(id);
	}
}
