package com.iplay.service.hotel;

import java.util.List;

import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.vo.hotel.PostHotelVO;

public interface HotelService {
	/**
	 * @param pageNumber zero-based
	 * @param pageSize
	 * @return
	 */
	List<SimplifiedHotelDTO> listHotel(int pageNumber, int pageSize);
	
	int saveHotel(PostHotelVO hotel);
	
	int addBanquetHall(PostBanquetHallVO baquetHallVO, int hotelId);
	
	
	
}
