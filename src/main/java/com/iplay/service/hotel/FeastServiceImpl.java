package com.iplay.service.hotel;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.component.util.DelimiterUtils;
import com.iplay.dao.hotel.FeastDAO;
import com.iplay.dto.hotel.FeastDTO;
import com.iplay.entity.hotel.FeastDO;
import com.iplay.service.storage.StorageService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.vo.hotel.PostFilesVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class FeastServiceImpl implements FeastService{
	
	@Autowired
	private FeastDAO feastDAO;
	
	@Autowired
	private StorageService storageService;

	@Override
	public FeastDTO findFeastById(int id) {
		FeastDO feastDO = feastDAO.findOne(id);
		if(feastDO==null)
			return null;
		String[] pictures = ResourcesUriBuilder.buildUris(feastDO.getPicturesAsArray());
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
		FeastDO findedFeastDO = feastDAO.findOne(feastVO.getId());
		if(findedFeastDO == null)
			return -1;
		FeastDO feast = new FeastDO(feastVO.getName(), feastVO.getPrice(), feastVO.getCourses(), findedFeastDO.getPictures());
		feast.setId(feastVO.getId());
		FeastDO savedFeast = feastDAO.save(feast);
		int feastId = savedFeast.getId();
		return feastId;
	}

	@Override
	public String[] addPictures(int feastId, PostFilesVO files) {
		FeastDO findedFeastDO = feastDAO.findOne(feastId);
		if(findedFeastDO == null)
			return null;
		MultipartFile[] mfs = files.getFiles();
		String[] savedFilenames = new String[mfs.length];
		for(int i=0;i<mfs.length;i++){
			savedFilenames[i] = storageService.store(mfs[i], UUIDNamingStrategy.generateUUID());
		}
		String pictures = DelimiterUtils.joinArray(savedFilenames, DelimiterUtils.PICTURE_DELIMITER);
		feastDAO.addPictures(feastId, pictures);
		return ResourcesUriBuilder.buildUris(savedFilenames);
	}

	@Override
	public boolean[] deletePictures(int feastId, FileDeletionVO fileDeletionVO) {
		FeastDO findedFeastDO = feastDAO.findOne(feastId);
		if(findedFeastDO == null)
			return null;
		String pictures = findedFeastDO.getPictures();
		String[] files = fileDeletionVO.getNames();
		boolean[] rs = new boolean[files.length];
		for(int i=0;i<files.length;i++){
			rs[i] = storageService.delete(files[i]);
			pictures = pictures.replace(files[i]+DelimiterUtils.PICTURE_DELIMITER, "");
		}
		findedFeastDO.setPictures(pictures);
		feastDAO.save(findedFeastDO);
		return rs;
	}

	@Override
	public boolean delteFeasts(EntityDeletionVO entityDeletionVO) {
		List<FeastDO> feasts = new LinkedList<>();
		feastDAO.findAll(entityDeletionVO.getIds()).forEach(f->{
			feasts.add(f);
		});
		feastDAO.delete(feasts);
		return true;
	}
}
