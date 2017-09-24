package com.iplay.web.hotel;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.dto.hotel.HotelDTO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.service.hotel.HotelService;
import com.iplay.vo.hotel.PostHotelVO;
import com.iplay.web.configuration.PaginationConfig;
import com.iplay.web.exception.ResourceNotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
	@Autowired
	private HotelService hotelService;
	
	@ApiOperation(notes = "管理員新增一個酒店，返回酒店id", value = "")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int addHotel(@Valid @ApiParam("酒店实体，属性包括：name, cityOfAddress, districtOfAddress,"
			+ "streetOfAddress, description, contact, telephone, email, files") PostHotelVO postHotelVO){
		return hotelService.saveHotel(postHotelVO);
	}
	
	@ApiOperation(notes = "管理員更新一個酒店，id为必填项", value = "")
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int updateHotel(@Valid @ApiParam("酒店实体，属性包括：id(必须项), name, cityOfAddress, districtOfAddress,"
			+ "streetOfAddress, description, contact, telephone, email, files(非必需)") PostHotelVO postHotelVO){
		return hotelService.saveHotel(postHotelVO);
	}
	
	@ApiOperation(notes = "管理員删除一个酒店，id为必填项", value = "")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteHotel(@ApiParam("酒店id")@PathVariable int id){
		return hotelService.deleteHotel(id);
	}
	
	@ApiOperation(notes="分页展示酒店",value="")
    @GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
	public List<SimplifiedHotelDTO>  listHotelsForUser(@ApiParam("从0开始的页码")@RequestParam int page){
		return hotelService.listHotel(page, PaginationConfig.HOTELS_PER_PAGE_FOR_ORDINARY_USER);
	}
	
	@ApiOperation(notes="根据酒店Id获得酒店详细信息",value="")
    @GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
	public HotelDTO  findHotelById(@ApiParam("酒店id")@PathVariable int id){
		HotelDTO hotel = hotelService.findHotelById(id);
		if(hotel==null)
			throw new ResourceNotFoundException("Hotel with id: "+id+"doesn't exist");
		return hotel;
	}
}
