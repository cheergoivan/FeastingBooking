package com.iplay.service.hotel;

import com.iplay.dto.hotel.BanquetHallDTO;
import com.iplay.vo.hotel.PostBanquetHallVO;

public interface BanquetHallService {
	
	BanquetHallDTO findBanquetHallById(int id);
	
	boolean deleteBanquetHall(int id);
	
	int updateBanquetHall(PostBanquetHallVO banquetHallVO);
	
}
