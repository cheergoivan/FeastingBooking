package com.iplay.service.advertisement;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.dao.advertisement.AdvertisementDAO;
import com.iplay.dto.advertisement.AdvertisementDTO;
import com.iplay.entity.advertisement.AdvertisementDO;
import com.iplay.service.storage.StorageService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class AdvertisementServiceImpl implements AdvertisementService{
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private AdvertisementDAO advertisementDAO;
	
	@Override
	public List<AdvertisementDTO> addAdvertisements(PostFilesVO postFilesVO) {
		List<AdvertisementDTO> rs = new LinkedList<>();
		MultipartFile[] files = postFilesVO.getFiles();
		for(MultipartFile file:files){
			String filename = storageService.store(file, UUIDNamingStrategy.generateUUID());
			AdvertisementDTO dto = new AdvertisementDTO(advertisementDAO.save(new
					AdvertisementDO(filename)).getId(), ResourcesUriBuilder.buildUri(filename));
			rs.add(dto);
		}
		return rs;
	}
	
	public boolean deleteAdvs(EntityDeletionVO vo){
		List<AdvertisementDO> advs = new LinkedList<>();
		List<String> pictures = new LinkedList<>();
		advertisementDAO.findAll(vo.getIds()).forEach(adv->{
			advs.add(adv);
			pictures.add(adv.getPicture());
		});
		advertisementDAO.delete(advs);
		storageService.delete(pictures);
		return true;
	}

	@Override
	public boolean deleteAdv(int id) {
		AdvertisementDO adv = advertisementDAO.findOne(id);
		if(adv != null){
			storageService.delete(adv.getPicture());
			advertisementDAO.delete(adv);
		}
		return true;
	}

	@Override
	public List<AdvertisementDTO> listAdvs() {
		List<AdvertisementDTO> advs = new LinkedList<>();
		advertisementDAO.findAll().forEach(adv->{
			advs.add(new AdvertisementDTO(adv.getId(), ResourcesUriBuilder.buildUri(adv.getPicture())));
		});
		return advs;
	}

}
