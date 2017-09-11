package com.iplay.service.hotel;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.dao.hotel.HotelDAO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.entity.hotel.AddressDO;
import com.iplay.entity.hotel.HotelDO;
import com.iplay.service.storage.StorageService;
import com.iplay.service.storage.naming.StorageNamingStrategy;
import com.iplay.vo.hotel.PostHotelVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class HotelServiceImpl implements HotelService {

	private static final String HOTEL_PICTURES_PREFIX = "hotel";

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
		AddressDO addressDO = new AddressDO(hotel.getAddress().getCity(), hotel.getAddress().getDistrict(),
				hotel.getAddress().getStreet());
		HotelDO hotelDO = new HotelDO(hotel.getName(), hotel.getDescription(), addressDO, hotel.getContact(),
				hotel.getTelephone(), hotel.getEmail(), pictures.length);
		HotelDO savedHotel = hotelDAO.save(hotelDO);
		int hotelId = savedHotel.getId();
		for (int i = 0; i < pictures.length; i++) {
			storageService.store(pictures[i], storageNamingStrategy.
					generateResourceName(HOTEL_PICTURES_PREFIX, hotelId, i));
		}
		return hotelId;
	}

}
