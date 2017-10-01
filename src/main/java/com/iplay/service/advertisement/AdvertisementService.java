package com.iplay.service.advertisement;

import java.util.List;

import com.iplay.dto.advertisement.AdvertisementDTO;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.PostFilesVO;

public interface AdvertisementService {
	
	List<AdvertisementDTO> addAdvertisements(PostFilesVO postFilesVO);
	
	boolean deleteAdvs(EntityDeletionVO vo);
	
	boolean deleteAdv(int id);
	
	List<AdvertisementDTO> listAdvs();
}
