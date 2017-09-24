package com.iplay.service.hotel;

import java.util.List;

import com.iplay.dto.hotel.HotelDTO;
import com.iplay.dto.hotel.SimplifiedHotelAdminDTO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
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
	
	int addBanquetHall(PostBanquetHallVO baquetHallVO, int hotelId);
	
	int addFeast(PostFeastVO feastVO, int hotelId);
	
	HotelDTO findHotelById(int id);
	
	double getHotelRating(int id);
}
