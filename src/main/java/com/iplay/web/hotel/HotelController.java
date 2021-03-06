package com.iplay.web.hotel;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iplay.dto.hotel.HotelDTO;
import com.iplay.dto.hotel.SimplifiedHotelAdminDTO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.service.exception.ResourceNotFoundException;
import com.iplay.service.hotel.HotelService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.vo.hotel.PostHotelVO;
import com.iplay.web.configuration.PaginationConfig;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/hotels")
@Validated
public class HotelController {
	@Autowired
	private HotelService hotelService;

	@ApiOperation(notes = "管理員新增一個酒店，返回酒店id", value = "")
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public int addHotel(@Valid @ApiParam("酒店实体，属性包括：name, cityOfAddress, districtOfAddress,"
			+ "streetOfAddress, description, contact, telephone, email") @RequestBody PostHotelVO postHotelVO) {
		postHotelVO.setId(-1);
		return hotelService.saveHotel(postHotelVO);
	}

	@ApiOperation(notes = "管理員更新一個酒店，id为必填项，返回酒店id", value = "")
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public int updateHotel(@ApiParam("酒店id") @PathVariable int id,
			@Valid @ApiParam("酒店实体，属性包括：name, cityOfAddress, districtOfAddress,"
					+ "streetOfAddress, description, contact, telephone, email") @RequestBody PostHotelVO postHotelVO) {
		postHotelVO.setId(id);
		int rs = hotelService.saveHotel(postHotelVO);
		if (rs == -1)
			throw new ResourceNotFoundException("Hotel with id: " + id + " doesn't exist");
		return id;
	}

	@ApiOperation(notes = "管理員删除一个酒店，id为必填项，返回boolean", value = "")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteHotel(@ApiParam("酒店id") @PathVariable int id) {
		return hotelService.deleteHotel(id);
	}

	@ApiOperation(notes = "管理員批量删除酒店，返回boolean", value = "")
	@DeleteMapping
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteHotel(@Valid @RequestBody @ApiParam("酒店id集合") EntityDeletionVO entityDeletionVO) {
		return hotelService.deleteHotels(entityDeletionVO);
	}

	@ApiOperation(notes = "分页展示酒店,返回酒店的List，默认每页展示10条数据", value = "")
	@GetMapping
	public List<SimplifiedHotelDTO> listHotelsForUser(@ApiParam("从0开始的页码") @RequestParam int page,
			@ApiParam("每页展示的条数，可选参数， 默认为10") @RequestParam(required = false, defaultValue = ""
					+ PaginationConfig.HOTELS_PER_PAGE_FOR_ORDINARY_USER)@Min(1) int size) {
		return hotelService.listHotel(page, size);
	}

	/*
	@ApiOperation(notes = "分页展示酒店,返回酒店的List，默认每页展示10条数据", value = "")
	@GetMapping
	public Page<SimplifiedHotelDTO> listHotelsForUser(@PageableDefault(value = 1, sort = { "id" }, 
		direction = Sort.Direction.DESC) Pageable pageable) {
		
		PaginationRequestValidationResult rs = PaginationRequestValidator.validatePageable(pageable, HotelDO.class);
		if(!rs.isValid())
			throw new InvalidRequestParametersException(rs.getMessage());
			
		return hotelService.listHotelsByPage(pageable);
	}
	*/
	

	/*
	 * @ApiOperation(notes="分页展示酒店， 返回page对象",value="")
	 * 
	 * @GetMapping public Page<SimplifiedHotelDTO>
	 * listHotelsByPage(@ApiParam("从0开始的页码")@RequestParam int page,
	 * 
	 * @ApiParam("每页显示的数量")@RequestParam int size){ return
	 * hotelService.listHotelsByPage(page, size); }
	 */

	@ApiOperation(notes = "管理員獲取酒店列表", value = "")
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public List<SimplifiedHotelAdminDTO> listHotelsForAdmin() {
		return hotelService.listHotel();
	}

	@ApiOperation(notes = "根据酒店Id获得酒店详细信息", value = "")
	@GetMapping("/{id}")
	// @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MANAGER')")
	public HotelDTO findHotelById(@ApiParam("酒店id") @PathVariable int id) {
		HotelDTO hotel = hotelService.findHotelById(id);
		if (hotel == null)
			throw new ResourceNotFoundException("Hotel with id: " + id + " doesn't exist");
		return hotel;
	}

	@ApiOperation(notes = "管理員新增一個宴会厅，返回宴会厅id", value = "")
	@PostMapping("/{id}/banquet_halls")
	@PreAuthorize("hasRole('ADMIN')")
	public int addBanquetHall(@ApiParam("酒店id") @PathVariable("id") int hotelId,
			@Valid @RequestBody @ApiParam("宴会厅实体，属性包括：name, area, minimumTables, maximumTables, minimumPrice, height, columns(String), "
					+ "shape(String), actualArea(String), colorOfTablecloth, extraInfo(String，配置使用;连接), files") PostBanquetHallVO banquetHallVO) {
		int id = hotelService.addBanquetHall(banquetHallVO, hotelId);
		if (id == -1)
			throw new ResourceNotFoundException("Hotel with id:" + hotelId + " doesn't exist");
		return id;
	}

	@ApiOperation(notes = "管理員新增一個宴席，返回宴席id", value = "")
	@PostMapping("/{id}/feasts")
	@PreAuthorize("hasRole('ADMIN')")
	public int addFeast(@ApiParam("酒店id") @PathVariable("id") int hotelId,
			@Valid @RequestBody @ApiParam("宴席实体，属性包括：name, price, courses(String，菜肴使用;连接)") PostFeastVO feastVO) {
		int id = hotelService.addFeast(feastVO, hotelId);
		if (id == -1)
			throw new ResourceNotFoundException("Hotel with id:" + hotelId + " doesn't exist");
		return id;
	}

	@ApiOperation(notes = "管理员删除图片", value = "")
	@DeleteMapping("/{id}/pictures")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean[] deletePictures(@ApiParam("酒店id") @PathVariable("id") int hotelId,
			@Valid @RequestBody @ApiParam("文件名集合") FileDeletionVO pictures) {
		boolean[] rs = hotelService.deletePictures(hotelId, pictures);
		if (rs == null)
			throw new ResourceNotFoundException("Hotel with id:" + hotelId + " doesn't exist");
		return rs;
	}

	@ApiOperation(notes = "管理员上传酒店图片，返回所有增加的图片uri", value = "")
	@PostMapping("/{id}/pictures")
	@PreAuthorize("hasRole('ADMIN')")
	public String[] savePictures(@ApiParam("酒店id") @PathVariable("id") int hotelId,
			@ApiParam("MultipartFile[] files") @Valid PostFilesVO postFilesVO) {
		String[] uris = hotelService.savePictures(hotelId, postFilesVO);
		if (uris == null)
			throw new ResourceNotFoundException("Hotel with id:" + hotelId + " doesn't exist");
		return uris;
	}
}