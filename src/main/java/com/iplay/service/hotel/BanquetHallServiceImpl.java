package com.iplay.service.hotel;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.dao.hotel.BanquetHallDAO;
import com.iplay.dto.hotel.BanquetHallDTO;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.service.storage.StorageService;
import com.iplay.service.storage.naming.StorageNamingStrategy;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class BanquetHallServiceImpl implements BanquetHallService{
	
	@Autowired
	private BanquetHallDAO banquetHallDAO;
	
	@Autowired
	private StorageNamingStrategy storageNamingStrategy;
	
	@Autowired
	private StorageService storageService;

	@Override
	public BanquetHallDTO findBanquetHallById(int id) {
		BanquetHallDO banquetHallDO = banquetHallDAO.findOne(id);
		if(banquetHallDO == null){
			return null;
		}
		BanquetHallDTO banquetHallDTO = new BanquetHallDTO();
		BeanUtils.copyProperties(banquetHallDO, banquetHallDTO);
		banquetHallDTO.setExtraInfos(banquetHallDO.getExtraInfo().split(";"));
		banquetHallDTO.setTableRange(new int[]{banquetHallDO.getMinimumTables(), banquetHallDO.getMaximumTables()});
		int numOfPictures = banquetHallDO.getNumOfPictures();
		String[] pictures = new String[numOfPictures];
		for(int i=0;i<numOfPictures;i++){
			pictures[i] = ResourcesUriBuilder.buildtUri(storageNamingStrategy.
					generateResourceName(PictureNamingPrefix.BANQUET_HALL_PICTURES_PREFIX, id, i));
		}
		banquetHallDTO.setPictureUrls(pictures);
		return banquetHallDTO;
	}

	@Override
	public boolean deleteBanquetHall(int id) {
		banquetHallDAO.delete(id);
		return true;
	}

	@Override
	public int updateBanquetHall(PostBanquetHallVO banquetHallVO) {
		if(banquetHallVO.getId() == -1)
			return -1;
		MultipartFile[] pictures = banquetHallVO.getFiles();
		if(pictures == null){
			pictures = new MultipartFile[0];
		}
		BanquetHallDO banquetHallDO = new BanquetHallDO();
		BeanUtils.copyProperties(banquetHallVO, banquetHallDO);
		banquetHallDO.setNumOfPictures(pictures.length);
		if(banquetHallVO.getFiles() == null){
			BanquetHallDO findedBanquetHall = banquetHallDAO.findOne(banquetHallVO.getId());
			if(findedBanquetHall!=null){
				banquetHallDO.setNumOfPictures(findedBanquetHall.getNumOfPictures());
			}else{
				return -1;
			}
		}
		BanquetHallDO savedBanquet = banquetHallDAO.save(banquetHallDO);
		int banquetHallId = savedBanquet.getId();
		for (int i = 0; i < pictures.length; i++) {
			storageService.store(pictures[i],
					storageNamingStrategy.generateResourceName(PictureNamingPrefix.BANQUET_HALL_PICTURES_PREFIX, banquetHallId, i));
		}
		return banquetHallId;
	}
}
