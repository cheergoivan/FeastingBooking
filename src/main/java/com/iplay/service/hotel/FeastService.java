package com.iplay.service.hotel;

import com.iplay.dto.hotel.FeastDTO;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.vo.hotel.PostFilesVO;

public interface FeastService {
	FeastDTO findFeastById(int id);
	
	boolean deleteFeast(int id);
	
	boolean delteFeasts(EntityDeletionVO entityDeletionVO);
	
	int updateFeast(PostFeastVO feastVO);
	
	String[] addPictures(int feastId, PostFilesVO files);
	
	boolean[] deletePictures(int feastId, FileDeletionVO fileDeletionVO);
}
