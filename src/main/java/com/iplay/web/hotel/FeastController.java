package com.iplay.web.hotel;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.dto.hotel.FeastDTO;
import com.iplay.service.hotel.FeastService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.web.exception.ResourceNotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/feasts")
public class FeastController {
	@Autowired
	private FeastService feastService;
	
	@ApiOperation(notes="根据宴席Id获得宴席详细信息",value="")
    @GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
	public FeastDTO findFeastById(@ApiParam("宴席id")@PathVariable int id){
		FeastDTO feast = feastService.findFeastById(id);
		if(feast==null)
			throw new ResourceNotFoundException("Feast with id: "+id+" doesn't exist");
		return feast;
	}
	
	@ApiOperation(notes="管理员修改一个宴席，返回宴席id",value="")
    @PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public int updateFeast(@ApiParam("宴席id")@PathVariable int id, @Valid 
			@ApiParam("宴席实体，属性包括：name, price, courses(String，菜肴使用;连接), files")@RequestBody PostFeastVO feastVO){
		feastVO.setId(id);
		int rs = feastService.updateFeast(feastVO); 
		if(rs == -1)
			throw new ResourceNotFoundException("Feast with id: "+id+" doesn't exist");
		return id;
	}
	
	@ApiOperation(notes="管理员删除一个宴席，返回boolean",value="")
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteFeast(@ApiParam("宴席id")@PathVariable int id){
		return feastService.deleteFeast(id); 
	}
	
	@ApiOperation(notes = "管理員批量删除宴席，返回boolean", value = "")
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteFeasts(@Valid@RequestBody@ApiParam("宴席id集合") EntityDeletionVO entityDeletionVO){
		return feastService.delteFeasts(entityDeletionVO);
	}
	
	@ApiOperation(notes="管理员删除一个宴席，返回boolean",value="")
    @DeleteMapping("/{id}/pictures")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean[] deleteFeast(@ApiParam("宴席id")@PathVariable int id,
			@Valid@RequestBody@ApiParam("文件名集合") FileDeletionVO fileDeletionVO){
		boolean[] rs = feastService.deletePictures(id, fileDeletionVO);
		if(rs == null)
			throw new ResourceNotFoundException("Feast with id: "+id+" doesn't exist");
		return rs; 
	}
	
	@ApiOperation(notes="管理员增加宴席图片，返回所有增加的图片uri",value="")
    @PostMapping("/{id}/pictures")
	@PreAuthorize("hasRole('ADMIN')")
	public String[] savePictures(@ApiParam("宴席id")@PathVariable int id,
			@ApiParam("MultipartFile[] files")@Valid PostFilesVO postFilesVO){
		String[] uris = feastService.addPictures(id, postFilesVO);
		if(uris == null)
			throw new ResourceNotFoundException("Feast with id: "+id+" doesn't exist");
		return uris;
	}
}
