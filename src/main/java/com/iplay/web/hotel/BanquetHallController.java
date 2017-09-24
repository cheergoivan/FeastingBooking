package com.iplay.web.hotel;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.dto.hotel.BanquetHallDTO;
import com.iplay.service.hotel.BanquetHallService;
import com.iplay.service.hotel.HotelService;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.web.exception.ResourceNotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/banquet_halls")
public class BanquetHallController {
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private BanquetHallService banquetHallService;
	
	@ApiOperation(notes = "管理員新增一個宴会厅，返回宴会厅id", value = "")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int addBanquetHall(@Valid @ApiParam("酒店id")int hotelId, @Valid 
			@ApiParam("宴会厅实体，属性包括：name, area, minimumTables, maximumTables, minimumPrice, height, columns(String), "
					+ "shape(String), actualArea(String), colorOfTablecloth, extraInfo(String，配置使用;连接), files")
	PostBanquetHallVO banquetHallVO){
		return hotelService.addBanquetHall(banquetHallVO, hotelId);
	}
	
	
	@ApiOperation(notes="根据宴会厅Id获得宴会厅详细信息",value="")
    @GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
	public BanquetHallDTO findBanquetHallById(@ApiParam("宴席id")@PathVariable int id){
		BanquetHallDTO banquetHall = banquetHallService.findBanquetHallById(id);
		if(banquetHall==null)
			throw new ResourceNotFoundException("Banquet hall with id: "+id+"doesn't exist");
		return banquetHall;
	}
	
	@ApiOperation(notes="管理员修改一个宴会厅，返回宴会厅id",value="")
    @PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int updateBanquetHall(@ApiParam("酒店id")int hotelId, @Valid 
			@ApiParam("宴会厅实体，属性包括：id(必须项), name, area, minimumTables, maximumTables, minimumPrice, height, columns(String), "
					+ "shape(String), actualArea(String), colorOfTablecloth, extraInfo(String), files(非必需)")
	PostBanquetHallVO banquetHallVO){
		return banquetHallService.updateBanquetHall(banquetHallVO);
	}
	
	@ApiOperation(notes="管理员删除一个宴会厅",value="")
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteBanquetHall(@ApiParam("宴会厅id")@PathVariable int id){
		return banquetHallService.deleteBanquetHall(id);
	}
	
	
}
