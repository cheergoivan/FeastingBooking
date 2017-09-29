package com.iplay.service.hotel;

import com.iplay.dto.hotel.BanquetHallDTO;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.vo.hotel.PostFilesVO;

public interface BanquetHallService {
	
	BanquetHallDTO findBanquetHallById(int id);
	
	boolean deleteBanquetHall(int id);
	
	int updateBanquetHall(PostBanquetHallVO banquetHallVO);
	
	boolean[] deletePictures(int id, FileDeletionVO fileDeletionVO);
	
	String[] savePictures(int id, PostFilesVO postFilesVO);
	
	boolean deleteBanquetHalls(EntityDeletionVO entityDeletionVO);
	
}
