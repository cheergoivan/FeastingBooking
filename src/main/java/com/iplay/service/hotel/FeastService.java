package com.iplay.service.hotel;

import com.iplay.dto.hotel.FeastDTO;
import com.iplay.vo.hotel.PostFeastVO;

public interface FeastService {
	FeastDTO findFeastById(int id);
	
	boolean deleteFeast(int id);
	
	int updateFeast(PostFeastVO feastVO);
}
