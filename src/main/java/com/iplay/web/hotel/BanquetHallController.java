package com.iplay.web.hotel;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.service.hotel.HotelService;
import com.iplay.vo.hotel.PostBanquetHallVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/banquet_halls")
public class BanquetHallController {
	@Autowired
	private HotelService hotelService;
	
	@ApiOperation(notes = "管理員新增一個宴会厅", value = "")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int addBanquetHall(@Valid @ApiParam("酒店id")int hotelId, @Valid 
			@ApiParam("宴会厅实体，属性包括：name, area, minimumTables, maximumTables, minimumPrice, height, columns(String), "
					+ "shape(String), actualArea(String), colorOfTablecloth, extraInfo(String), files")
	PostBanquetHallVO banquetHallVO){
		return hotelService.addBanquetHall(banquetHallVO, hotelId);
	}
	
	
	
}