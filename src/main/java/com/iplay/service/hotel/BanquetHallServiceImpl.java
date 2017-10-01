package com.iplay.service.hotel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.component.util.DelimiterUtils;
import com.iplay.dao.hotel.BanquetHallDAO;
import com.iplay.dto.hotel.BanquetHallDTO;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.service.storage.StorageService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class BanquetHallServiceImpl implements BanquetHallService{
	
	@Autowired
	private BanquetHallDAO banquetHallDAO;
	
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
		String[] pictures =  ResourcesUriBuilder.buildUris(banquetHallDO.getPicturesAsArray());
		banquetHallDTO.setPictureUrls(pictures);
		return banquetHallDTO;
	}

	@Override
	public boolean deleteBanquetHall(int id) {
		BanquetHallDO banquetHall = banquetHallDAO.findOne(id);
		if(banquetHall!=null){
			storageService.delete(banquetHall.getPicturesAsArray());
			banquetHallDAO.delete(id);
		}
		return true;
	}
	
	

	@Override
	public int updateBanquetHall(PostBanquetHallVO banquetHallVO) {
		if(banquetHallVO.getId() == -1)
			return -1;
		BanquetHallDO findedBanquetHallDO = banquetHallDAO.findOne(banquetHallVO.getId());
		if(findedBanquetHallDO==null)
			return -1;
		BanquetHallDO banquetHallDO = new BanquetHallDO();
		BeanUtils.copyProperties(banquetHallVO, banquetHallDO);
		banquetHallDO.setPictures(findedBanquetHallDO.getPictures());
		BanquetHallDO savedBanquet = banquetHallDAO.save(banquetHallDO);
		int banquetHallId = savedBanquet.getId();
		return banquetHallId;
	}

	@Override
	public boolean[] deletePictures(int id, FileDeletionVO fileDeletionVO) {
		BanquetHallDO findedBanquetHallDO = banquetHallDAO.findOne(id);
		if(findedBanquetHallDO==null)
			return null;
		String pictures = findedBanquetHallDO.getPictures();
		String[] files = fileDeletionVO.getNames();
		boolean[] rs = new boolean[files.length];
		for(int i=0;i<files.length;i++){
			rs[i] = storageService.delete(files[i]);
			pictures = pictures.replace(files[i]+DelimiterUtils.PICTURE_DELIMITER, "");
		}
		findedBanquetHallDO.setPictures(pictures);
		banquetHallDAO.save(findedBanquetHallDO);
		return rs;
	}

	@Override
	public String[] savePictures(int id, PostFilesVO postFilesVO) {
		BanquetHallDO findedBanquetHallDO = banquetHallDAO.findOne(id);
		if(findedBanquetHallDO==null)
			return null;
		MultipartFile[] mfs = postFilesVO.getFiles();
		String[] savedFilenames = new String[mfs.length];
		for(int i=0;i<mfs.length;i++){
			savedFilenames[i] = storageService.store(mfs[i], UUIDNamingStrategy.generateUUID());
		}
		String pictures = DelimiterUtils.joinArray(savedFilenames, DelimiterUtils.PICTURE_DELIMITER);
		banquetHallDAO.addPictures(id, pictures);
		return ResourcesUriBuilder.buildUris(savedFilenames);
	}

	@Override
	public boolean deleteBanquetHalls(EntityDeletionVO entityDeletionVO) {
		List<BanquetHallDO> bhs = new LinkedList<>();
		List<String> pictures = new LinkedList<>();
		banquetHallDAO.findAll(entityDeletionVO.getIds()).forEach(bh->{
			pictures.addAll(Arrays.asList(bh.getPicturesAsArray()));
			bhs.add(bh);
		});
		banquetHallDAO.delete(bhs);
		storageService.delete(pictures);
		return true;
	}
	
	
}
