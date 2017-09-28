package com.iplay.web.hotel;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.configuration.security.jwtAuthentication.auth.UserContext;
import com.iplay.dto.hotel.HotelDTO;
import com.iplay.dto.hotel.SimplifiedHotelAdminDTO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.service.hotel.HotelService;
import com.iplay.vo.hotel.FileDeletionVO;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.vo.hotel.PostFilesVO;
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
			+ "streetOfAddress, description, contact, telephone, email, files") @RequestBody PostHotelVO postHotelVO){
		postHotelVO.setId(-1);
		return hotelService.saveHotel(postHotelVO);
	}
	
	@ApiOperation(notes = "管理員更新一個酒店，id为必填项", value = "")
	@PostMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public int updateHotel(@ApiParam("酒店id")@PathVariable int id, @Valid @ApiParam("酒店实体，属性包括：name, cityOfAddress, districtOfAddress,"
			+ "streetOfAddress, description, contact, telephone, email, files(非必需)") @RequestBody PostHotelVO postHotelVO){
		postHotelVO.setId(id);
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
	
	@ApiOperation(notes="管理員獲取酒店列表",value="")
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<SimplifiedHotelAdminDTO> listHotelsForAdmin() {
		return hotelService.listHotel();
	}
	
	@ApiOperation(notes="根据酒店Id获得酒店详细信息",value="")
    @GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
	public HotelDTO  findHotelById(@ApiParam("酒店id")@PathVariable int id){
		HotelDTO hotel = hotelService.findHotelById(id);
		if(hotel==null)
			throw new ResourceNotFoundException("Hotel with id: "+id+" doesn't exist");
		return hotel;
	}
	
	@ApiOperation(notes = "管理員新增一個宴会厅，返回宴会厅id", value = "")
	@PostMapping("/{id}/banquet_halls")
	@PreAuthorize("hasRole('ADMIN')")
	public int  addBanquetHall(@ApiParam("酒店id")@PathVariable("id") int hotelId, @Valid 
			@ApiParam("宴会厅实体，属性包括：name, area, minimumTables, maximumTables, minimumPrice, height, columns(String), "
					+ "shape(String), actualArea(String), colorOfTablecloth, extraInfo(String，配置使用;连接), files")
	PostBanquetHallVO banquetHallVO){
		int id = hotelService.addBanquetHall(banquetHallVO, hotelId);
		if(id == -1)
			throw new ResourceNotFoundException("Hotel with id:"+hotelId+" doesn't exist");
		return id;
	}
	
	@ApiOperation(notes = "管理員新增一個宴席，返回宴席id", value = "")
	@PostMapping("/{id}/feasts")
	@PreAuthorize("hasRole('ADMIN')")
	public int addFeast(@ApiParam("酒店id")@PathVariable("id") int hotelId, @Valid 
			@ApiParam("宴席实体，属性包括：name, price, courses(String，菜肴使用;连接), files")PostFeastVO feastVO){
		int id = hotelService.addFeast(feastVO, hotelId);
		if(id == -1)
			throw new ResourceNotFoundException("Hotel with id:"+hotelId+" doesn't exist");
		return id;
	}
	
	@ApiOperation(notes="用户对酒店进行打分",value="")
    @PostMapping("/{id}/rating")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER')")
	public boolean postRating(@ApiParam("酒店id")@PathVariable("id") int hotelId, 
			@ApiParam("评分") double score, @AuthenticationPrincipal UserContext context){
		return hotelService.updateHotelRating(context.getUserId(), hotelId, score);
	}
	
	@ApiOperation(notes="用户获得酒店评分",value="")
    @GetMapping("/{id}/rating")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
	public double getRating(@ApiParam("酒店id")@PathVariable("id") int hotelId){
		return hotelService.getHotelRating(hotelId);
	}
	
	@ApiOperation(notes="管理员删除图片",value="")
    @DeleteMapping("/{id}/pictures")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean[] deletePictures(@ApiParam("酒店id")@PathVariable("id") int hotelId, 
			@Valid@RequestBody FileDeletionVO pictures){
		boolean[] rs = hotelService.deletePictures(hotelId, pictures);
		if(rs==null)
			throw new ResourceNotFoundException("Hotel with id:"+hotelId+" doesn't exist");
		return rs;
	}
	
	@ApiOperation(notes="管理员上传酒店图片",value="")
    @PostMapping("/{id}/pictures")
	@PreAuthorize("hasRole('ADMIN')")
	public String[]  savePictures(@ApiParam("酒店id")@PathVariable("id") int hotelId, 
			@ApiParam("文件名集合")@Valid PostFilesVO postFilesVO){
		return hotelService.savePictures(hotelId, postFilesVO);
	}
}