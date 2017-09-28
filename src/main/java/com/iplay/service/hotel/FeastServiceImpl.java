package com.iplay.service.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.dao.hotel.FeastDAO;
import com.iplay.dto.hotel.FeastDTO;
import com.iplay.entity.hotel.FeastDO;
import com.iplay.service.storage.StorageService;
import com.iplay.service.storage.naming.StorageNamingStrategy;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class FeastServiceImpl implements FeastService{
	
	@Autowired
	private FeastDAO feastDAO;
	
	@Autowired
	private StorageNamingStrategy storageNamingStrategy;
	
	@Autowired
	private StorageService storageService;

	@Override
	public FeastDTO findFeastById(int id) {
		FeastDO feastDO = feastDAO.findOne(id);
		if(feastDO==null)
			return null;
		int numOfPictures = feastDO.getNumOfPictures();
		String[] pictures = new String[numOfPictures];
		for(int i=0;i<numOfPictures;i++){
			pictures[i] = ResourcesUriBuilder.buildUri(storageNamingStrategy.
					generateResourceName(PictureNamingPrefix.FEAST_PICTURES_PREFIX, id, i));
		}
		return new FeastDTO(id, feastDO.getName(), feastDO.getPrice(), feastDO.getCourses().split(";"), pictures);
	}

	@Override
	public boolean deleteFeast(int id) {
		feastDAO.delete(id);
		return true;
	}

	@Override
	public int updateFeast(PostFeastVO feastVO) {
		if(feastVO.getId() == -1)
			return -1;
		MultipartFile[] pictures = feastVO.getFiles();
		if(pictures == null){
			pictures = new MultipartFile[0];
		}
		FeastDO feast = new FeastDO(feastVO.getName(), feastVO.getPrice(), feastVO.getCourses(), pictures.length);
		if(feastVO.getFiles() == null){
			FeastDO findedFeast = feastDAO.findOne(feastVO.getId());
			if(findedFeast!=null){
				feast.setNumOfPictures(findedFeast.getNumOfPictures());
			}else{
				return -1;
			}
		}
		feast.setId(feastVO.getId());
		FeastDO savedFeast = feastDAO.save(feast);
		int feastId = savedFeast.getId();
		for (int i = 0; i < pictures.length; i++) {
			storageService.store(pictures[i],
					storageNamingStrategy.generateResourceName(PictureNamingPrefix.FEAST_PICTURES_PREFIX, feastId, i));
		}
		return feastId;
	}
}
