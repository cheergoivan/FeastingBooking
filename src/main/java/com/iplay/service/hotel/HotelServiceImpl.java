package com.iplay.service.hotel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.dao.hotel.HotelDAO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.entity.hotel.AddressDO;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.entity.hotel.HotelDO;
import com.iplay.service.storage.StorageService;
import com.iplay.service.storage.naming.StorageNamingStrategy;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.vo.hotel.PostHotelVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class HotelServiceImpl implements HotelService {

	private static final String HOTEL_PICTURES_PREFIX = "hotel";
	
	private static final String BANQUET_HALL_PICTURES_PREFIX = "banquethall";

	@Autowired
	private StorageService storageService;

	@Autowired
	private StorageNamingStrategy storageNamingStrategy;

	@Autowired
	private HotelDAO hotelDAO;

	@Override
	public List<SimplifiedHotelDTO> listHotel(int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, null);
		Page<HotelDO> hotelPages = hotelDAO.findAll(pageRequest);
		List<HotelDO> hotelDOs = hotelPages.getContent();
		List<SimplifiedHotelDTO> hotels = hotelDOs.stream().map(hotelDO->{
			return new SimplifiedHotelDTO(hotelDO.getId(), hotelDO.getName(),
					new double[]{hotelDO.getMinimumPrice(), hotelDO.getMaximumPrice()},
					new int[]{hotelDO.getMinimumTables(), hotelDO.getMaximunTables()},
					0, ResourcesUriBuilder.buildtUri(storageNamingStrategy.
					generateResourceName(HOTEL_PICTURES_PREFIX, hotelDO.getId(), 0)));
		}).collect(Collectors.toList());
		return hotels;
	}
	
	@Override
	public int saveHotel(PostHotelVO hotel) {
		MultipartFile[] pictures = hotel.getFiles();
		AddressDO addressDO = new AddressDO(hotel.getCityOfAddress(), hotel.getDistrictOfAddress(),
				hotel.getStreetOfAddress());
		HotelDO hotelDO = new HotelDO(hotel.getName(), hotel.getDescription(), addressDO, hotel.getContact(),
				hotel.getTelephone(), hotel.getEmail(), pictures.length);
		if(hotel.getId()!=-1){
			hotelDO.setId(hotel.getId());
		}
		HotelDO savedHotel = hotelDAO.save(hotelDO);
		int hotelId = savedHotel.getId();
		for (int i = 0; i < pictures.length; i++) {
			storageService.store(pictures[i], storageNamingStrategy.
					generateResourceName(HOTEL_PICTURES_PREFIX, hotelId, i));
		}
		return hotelId;
	}

	@Override
	@Transactional
	public int addBanquetHall(PostBanquetHallVO baquetHallVO, int hotelId) {
		BanquetHallDO banquetHallDO = new BanquetHallDO();
		BeanUtils.copyProperties(baquetHallVO, banquetHallDO);
		MultipartFile[] pictures = baquetHallVO.getFiles();
		banquetHallDO.setNumOfPictures(pictures.length);
		HotelDO hotel = hotelDAO.findOne(hotelId);
		List<BanquetHallDO> bhs = new LinkedList<>();
		bhs.add(banquetHallDO);
		hotel.setBanquetHalls(bhs);
		hotelDAO.save(hotel);
		for (int i = 0; i < pictures.length; i++) {
			storageService.store(pictures[i], storageNamingStrategy.
					generateResourceName(BANQUET_HALL_PICTURES_PREFIX, hotelId, i));
		}
		return banquetHallDO.getId();
	}

}
