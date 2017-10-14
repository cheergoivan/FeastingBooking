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

import com.iplay.dto.hotel.BanquetHallDTO;
import com.iplay.service.exception.ResourceNotFoundException;
import com.iplay.service.hotel.BanquetHallService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.hotel.PostBanquetHallVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/banquet_halls")
public class BanquetHallController {
	@Autowired
	private BanquetHallService banquetHallService;
	
	@ApiOperation(notes="根据宴会厅Id获得宴会厅详细信息",value="")
    @GetMapping("/{id}")
	//@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
	public BanquetHallDTO findBanquetHallById(@ApiParam("宴席id")@PathVariable int id){
		BanquetHallDTO banquetHall = banquetHallService.findBanquetHallById(id);
		if(banquetHall==null)
			throw new ResourceNotFoundException("Banquet hall with id: "+id+" doesn't exist");
		return banquetHall;
	}
	
	@ApiOperation(notes="管理员修改一个宴会厅，返回宴会厅id",value="")
    @PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public int updateBanquetHall(@ApiParam("宴会厅id")@PathVariable int id, @Valid 
			@ApiParam("宴会厅实体，属性包括： name, area, minimumTables, maximumTables, minimumPrice, height, columns(String), "
					+ "shape(String), actualArea(String), colorOfTablecloth, extraInfo(String)")@RequestBody
	PostBanquetHallVO banquetHallVO){
		banquetHallVO.setId(id);
		int rs = banquetHallService.updateBanquetHall(banquetHallVO);
		if(rs == -1)
			throw new ResourceNotFoundException("Banquet hall with id: "+id+" doesn't exist");
		return rs;
	}
	
	@ApiOperation(notes="管理员删除一个宴会厅",value="")
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteBanquetHall(@ApiParam("宴会厅id")@PathVariable int id){
		return banquetHallService.deleteBanquetHall(id);
	}
	
	
	@ApiOperation(notes = "管理員批量删除宴会厅，返回boolean", value = "")
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deletebanquetHalls(@Valid@RequestBody@ApiParam("宴会厅id集合") EntityDeletionVO entityDeletionVO){
		return banquetHallService.deleteBanquetHalls(entityDeletionVO);
	}
	
	@ApiOperation(notes="管理员删除宴会厅图片，返回boolean[]",value="")
    @DeleteMapping("/{id}/pictures")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean[] deletePictures(@ApiParam("宴会厅id")@PathVariable("id") int id, 
			@Valid@RequestBody@ApiParam("文件名集合") FileDeletionVO pictures){
		boolean[] rs = banquetHallService.deletePictures(id, pictures);
		if(rs==null)
			throw new ResourceNotFoundException("Hotel with id:"+id+" doesn't exist");
		return rs;
	}
	
	@ApiOperation(notes="管理员上传宴会厅图片，返回所有增加的图片uri",value="")
    @PostMapping("/{id}/pictures")
	@PreAuthorize("hasRole('ADMIN')")
	public String[]  savePictures(@ApiParam("宴会厅id")@PathVariable("id") int id, 
			@ApiParam("MultipartFile[] files")@Valid PostFilesVO postFilesVO){
		String[] uris = banquetHallService.savePictures(id, postFilesVO);
		if(uris==null)
			throw new ResourceNotFoundException("Hotel with id:"+id+" doesn't exist");
		return uris;
	}
	
	
}
