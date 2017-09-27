package com.iplay.web.hotel;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.dto.hotel.FeastDTO;
import com.iplay.service.hotel.FeastService;
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
	
	@ApiOperation(notes="管理员修改一个宴席",value="")
    @PostMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public int updateFeast(@ApiParam("宴席id")@PathVariable int id, @Valid 
			@ApiParam("宴席实体，属性包括：name, price, courses(String，菜肴使用;连接), files")PostFeastVO feastVO){
		feastVO.setId(-1);
		return feastService.updateFeast(feastVO); 
	}
	
	@ApiOperation(notes="管理员删除一个宴席",value="")
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteFeast(@ApiParam("宴席id")@PathVariable int id){
		return feastService.deleteFeast(id); 
	}
}
