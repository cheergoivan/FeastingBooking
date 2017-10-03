package com.iplay.service.hotel;

import java.util.List;

import org.springframework.data.domain.Page;

import com.iplay.dto.hotel.HotelDTO;
import com.iplay.dto.hotel.SimplifiedHotelAdminDTO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.entity.hotel.HotelDO;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.vo.hotel.PostHotelVO;

public interface HotelService {
	/**
	 * @param pageNumber zero-based
	 * @param pageSize
	 * @return
	 */
	List<SimplifiedHotelDTO> listHotel(int pageNumber, int pageSize);
	
	List<SimplifiedHotelAdminDTO> listHotel();
	
	int saveHotel(PostHotelVO hotel);
	
	boolean deleteHotel(int id);
	
	int addBanquetHall(PostBanquetHallVO banquetHallVO, int hotelId);
	
	int addFeast(PostFeastVO feastVO, int hotelId);
	
	HotelDTO findHotelById(int id);
	
	double getHotelRating(int id);
	
	double updateHotelRating(int userId, int hotelId, double score);
	
	boolean hasUserRatedHotel(int userId, int hotelId);
	
	String[] savePictures(int hotelId, PostFilesVO files);
	
	boolean[] deletePictures(int hotelId, FileDeletionVO fileDeletionVO);
	
	boolean deleteHotels(EntityDeletionVO entityDeletionVO);
	
	void updateTableRange(HotelDO hotel, int minimumTables, int maximumTables);
	
	void updatePriceRange(HotelDO hotel, double minimumPrice, double maximumPrice);
	
	void reCalculateTableRange(int hotelId);
	
	void reClaculatePriceRange(int hotelId);
	
	Page<SimplifiedHotelDTO> listHotelsByPage(int page, int size);
}
