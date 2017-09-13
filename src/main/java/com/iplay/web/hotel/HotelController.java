package com.iplay.web.hotel;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.service.hotel.HotelService;
import com.iplay.vo.hotel.PostHotelVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
	@Autowired
	private HotelService hotelService;
	
	@ApiOperation(notes = "管理員上傳一個酒店", value = "")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int addHotel(@Valid@RequestBody@ApiParam("酒店实体") PostHotelVO postHotelVO){
		return hotelService.saveHotel(postHotelVO);
	}
	
}
