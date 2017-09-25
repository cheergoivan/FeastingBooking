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
import com.iplay.dao.hotel.HotelRatingDAO;
import com.iplay.dto.hotel.HotelDTO;
import com.iplay.dto.hotel.SimplifiedBanquetHallDTO;
import com.iplay.dto.hotel.SimplifiedFeastDTO;
import com.iplay.dto.hotel.SimplifiedHotelAdminDTO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.entity.hotel.AddressDO;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.entity.hotel.FeastDO;
import com.iplay.entity.hotel.HotelDO;
import com.iplay.entity.hotel.HotelRatingDO;
import com.iplay.service.storage.StorageService;
import com.iplay.service.storage.naming.StorageNamingStrategy;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.vo.hotel.PostHotelVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private StorageService storageService;

	@Autowired
	private StorageNamingStrategy storageNamingStrategy;

	@Autowired
	private HotelDAO hotelDAO;

	@Autowired
	private HotelRatingDAO hotelRatingDAO;

	@Override
	public List<SimplifiedHotelDTO> listHotel(int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, null);
		Page<HotelDO> hotelPages = hotelDAO.findAll(pageRequest);
		List<HotelDO> hotelDOs = hotelPages.getContent();
		List<SimplifiedHotelDTO> hotels = hotelDOs.stream().map(hotelDO -> {
			return new SimplifiedHotelDTO(hotelDO.getId(), hotelDO.getName(),
					new double[] { hotelDO.getMinimumPrice(), hotelDO.getMaximumPrice() },
					new int[] { hotelDO.getMinimumTables(), hotelDO.getMaximunTables() }, 0,
					ResourcesUriBuilder.buildtUri(storageNamingStrategy
							.generateResourceName(PictureNamingPrefix.HOTEL_PICTURES_PREFIX, hotelDO.getId(), 0)));
		}).collect(Collectors.toList());
		return hotels;
	}

	@Override
	public List<SimplifiedHotelAdminDTO> listHotel() {
		// TODO Auto-generated method stub
		Iterable<HotelDO> hotels = hotelDAO.findAll();
		List<SimplifiedHotelAdminDTO> hotelList = new LinkedList<SimplifiedHotelAdminDTO>();
		for(HotelDO hotel : hotels) {
			hotelList.add(new SimplifiedHotelAdminDTO(hotel.getId(), hotel.getName(), hotel.getContact(), hotel.getTelephone(), hotel.getEmail(), hotel.getAddress().formatAsString()));
		}
		return hotelList;
	}
	
	@Override
	public int saveHotel(PostHotelVO hotel) {
		MultipartFile[] pictures = hotel.getFiles();
		if(pictures==null)
			pictures = new MultipartFile[0];
		AddressDO addressDO = new AddressDO(hotel.getCityOfAddress(), hotel.getDistrictOfAddress(),
				hotel.getStreetOfAddress());
		HotelDO hotelDO = new HotelDO(hotel.getName(), hotel.getDescription(), addressDO, hotel.getContact(),
				hotel.getTelephone(), hotel.getEmail(), pictures.length);
		boolean isCreated = true;
		if (hotel.getId() != -1) {
			isCreated = false;
			if(hotel.getFiles()==null){
				HotelDO findedHotel = hotelDAO.findOne(hotel.getId());
				if(findedHotel != null){
					hotelDO.setNumOfPictures(findedHotel.getNumOfPictures());
					hotelDO.setId(hotel.getId());
				}else{
					return -1;
				}
			}
		}
		HotelDO savedHotel = hotelDAO.save(hotelDO);
		int hotelId = savedHotel.getId();
		for (int i = 0; i < pictures.length; i++) {
			storageService.store(pictures[i],
					storageNamingStrategy.generateResourceName(PictureNamingPrefix.HOTEL_PICTURES_PREFIX, hotelId, i));
		}
		if (isCreated) {
			hotelRatingDAO.save(new HotelRatingDO(hotelId, 0.0, 0));
		}
		return hotelId;
	}

	@Override
	@Transactional
	public int addBanquetHall(PostBanquetHallVO banquetHallVO, int hotelId) {
		BanquetHallDO banquetHallDO = new BanquetHallDO();
		BeanUtils.copyProperties(banquetHallVO, banquetHallDO);
		MultipartFile[] pictures = banquetHallVO.getFiles();
		banquetHallDO.setNumOfPictures(pictures.length);
		HotelDO hotel = hotelDAO.findOne(hotelId);
		if(hotel==null)
			return -1;
		List<BanquetHallDO> bhs = new LinkedList<>();
		bhs.add(banquetHallDO);
		hotel.setBanquetHalls(bhs);
		hotelDAO.save(hotel);
		for (int i = 0; i < pictures.length; i++) {
			storageService.store(pictures[i], storageNamingStrategy
					.generateResourceName(PictureNamingPrefix.BANQUET_HALL_PICTURES_PREFIX, hotelId, i));
		}
		return bhs.get(0).getId();
	}

	@Override
	public int addFeast(PostFeastVO feastVO, int hotelId) {
		FeastDO feastDO = new FeastDO();
		BeanUtils.copyProperties(feastVO, feastDO, "id");
		MultipartFile[] pictures = feastVO.getFiles();
		feastDO.setNumOfPictures(pictures.length);
		HotelDO hotel = hotelDAO.findOne(hotelId);
		if(hotel==null)
			return -1;
		List<FeastDO> feasts = new LinkedList<>();
		feasts.add(feastDO);
		hotel.setFeasts(feasts);
		hotelDAO.save(hotel);
		for (int i = 0; i < pictures.length; i++) {
			storageService.store(pictures[i],
					storageNamingStrategy.generateResourceName(PictureNamingPrefix.FEAST_PICTURES_PREFIX, hotelId, i));
		}
		return feasts.get(0).getId();
	}

	@Override
	public HotelDTO findHotelById(int id) {
		HotelDO hotel = hotelDAO.findOne(id);
		if(hotel == null)
			return null;
		List<SimplifiedFeastDTO> feasts = hotel.getFeasts().stream()
				.map(feastDO -> new SimplifiedFeastDTO(feastDO.getId(), feastDO.getName(), feastDO.getPrice()))
				.collect(Collectors.toList());
		List<SimplifiedBanquetHallDTO> banquetHalls = hotel.getBanquetHalls().stream()
				.map(banquetHallDO -> new SimplifiedBanquetHallDTO(banquetHallDO.getId(), banquetHallDO.getArea(),
						new int[] { banquetHallDO.getMinimumTables(), banquetHallDO.getMaximumTables() },
						banquetHallDO.getMinimumPrice(),
						ResourcesUriBuilder.buildtUri(storageNamingStrategy.generateResourceName(
								PictureNamingPrefix.BANQUET_HALL_PICTURES_PREFIX, banquetHallDO.getId(), 0))))
				.collect(Collectors.toList());
		
		String[] pictures = new String[hotel.getNumOfPictures()];
		for(int i=0;i<pictures.length;i++){
			pictures[i] = ResourcesUriBuilder.buildtUri(storageNamingStrategy.generateResourceName(
					PictureNamingPrefix.HOTEL_PICTURES_PREFIX, hotel.getId(), i));
		}
		
		return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getDescription(), 
				hotel.getAddress().formatAsString(),
				hotel.getContact(), hotel.getTelephone(), hotel.getEmail(),
				new int[]{hotel.getMinimumTables(), hotel.getMaximunTables()},
				new double[]{hotel.getMinimumPrice(), hotel.getMaximumPrice()},
				getHotelRating(hotel.getId()), banquetHalls, feasts, pictures);
	}

	@Override
	public double getHotelRating(int id) {
		HotelRatingDO rating = hotelRatingDAO.findOne(id);
		return rating.getTimes()==0? 5.0:rating.getTotalScore()/rating.getTimes();
	}

	@Override
	public boolean deleteHotel(int id) {
		hotelDAO.delete(id);
		return true;
	}

}
