package com.iplay.web.hotel;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.service.hotel.HotelService;
import com.iplay.vo.hotel.PostFeastVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/feasts")
public class FeastController {
	@Autowired
	private HotelService hotelService;
	
	@ApiOperation(notes = "管理員新增一個宴席", value = "")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int addFeast(@Valid @ApiParam("酒店id")int hotelId, @Valid 
			@ApiParam("宴席实体，属性包括：name, price, courses(String), files")PostFeastVO feastVO){
		return hotelService.addFeast(feastVO, hotelId);
	}
	
}
