package com.iplay.service.hotel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iplay.component.naming.UUIDNamingStrategy;
import com.iplay.component.util.DelimiterUtils;
import com.iplay.dao.hotel.BanquetHallDAO;
import com.iplay.dao.hotel.FeastDAO;
import com.iplay.dao.hotel.HotelDAO;
import com.iplay.dao.hotel.rating.HotelRatingDAO;
import com.iplay.dao.hotel.rating.HotelRatingRecordDAO;
import com.iplay.dto.hotel.AddressDTO;
import com.iplay.dto.hotel.HotelDTO;
import com.iplay.dto.hotel.SimplifiedBanquetHallDTO;
import com.iplay.dto.hotel.SimplifiedFeastDTO;
import com.iplay.dto.hotel.SimplifiedHotelAdminDTO;
import com.iplay.dto.hotel.SimplifiedHotelDTO;
import com.iplay.entity.hotel.AddressDO;
import com.iplay.entity.hotel.BanquetHallDO;
import com.iplay.entity.hotel.FeastDO;
import com.iplay.entity.hotel.HotelDO;
import com.iplay.entity.hotel.rating.HotelRatingDO;
import com.iplay.entity.hotel.rating.HotelRatingRecordDO;
import com.iplay.service.storage.StorageService;
import com.iplay.vo.common.EntityDeletionVO;
import com.iplay.vo.common.FileDeletionVO;
import com.iplay.vo.common.PostFilesVO;
import com.iplay.vo.hotel.PostBanquetHallVO;
import com.iplay.vo.hotel.PostFeastVO;
import com.iplay.vo.hotel.PostHotelVO;
import com.iplay.web.resource.ResourcesUriBuilder;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private StorageService storageService;

	@Autowired
	private HotelDAO hotelDAO;

	@Autowired
	private HotelRatingDAO hotelRatingDAO;
	
	@Autowired
	private HotelRatingRecordDAO hotelRatingRecordDAO;
	
	@Autowired
	private BanquetHallDAO banquetHallDAO;
	
	@Autowired
	private FeastDAO feastDAO;

	@Override
	public List<SimplifiedHotelDTO> listHotel(int pageNumber, int pageSize) {
		PageRequest pageRequest = new PageRequest(pageNumber, pageSize, null);
		Page<HotelDO> hotelPages = hotelDAO.findAll(pageRequest);
		List<HotelDO> hotelDOs = hotelPages.getContent();
		List<SimplifiedHotelDTO> hotels = hotelDOs.stream().map(hotelDO -> {
			return new SimplifiedHotelDTO(hotelDO.getId(), hotelDO.getName(),
					new double[]{hotelDO.getMinimumPrice()==Double.MAX_VALUE?0:hotelDO.getMinimumPrice(), hotelDO.getMaximumPrice()==-1?0:hotelDO.getMaximumPrice()},
					new int[]{hotelDO.getMinimumTables()==Integer.MAX_VALUE?0:hotelDO.getMinimumTables(), hotelDO.getMaximunTables()==-1?0: hotelDO.getMaximunTables()},0,
					hotelDO.getPicturesAsArray().length>0?ResourcesUriBuilder.buildUri(hotelDO.getPicturesAsArray()[0]):"");
		}).collect(Collectors.toList());
		return hotels;
	}
	

	@Override
	public Page<SimplifiedHotelDTO> listHotelsByPage(int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size, null);
		Page<HotelDO> hotelPages = hotelDAO.findAll(pageRequest);
		return hotelPages.map(hotelDO -> {
			return new SimplifiedHotelDTO(hotelDO.getId(), hotelDO.getName(),
					new double[] { hotelDO.getMinimumPrice(), hotelDO.getMaximumPrice() },
					new int[] { hotelDO.getMinimumTables(), hotelDO.getMaximunTables() }, 0,
					hotelDO.getPicturesAsArray().length>0?ResourcesUriBuilder.buildUri(hotelDO.getPicturesAsArray()[0]):"");
		});
	}
	

	@Override
	public List<SimplifiedHotelAdminDTO> listHotel() {
		Iterable<HotelDO> hotels = hotelDAO.findAll();
		List<SimplifiedHotelAdminDTO> hotelList = new LinkedList<SimplifiedHotelAdminDTO>();
		for(HotelDO hotel : hotels) {
			hotelList.add(new SimplifiedHotelAdminDTO(hotel.getId(), hotel.getName(), hotel.getContact(), hotel.getTelephone(), hotel.getEmail(), hotel.getAddress().formatAsString()));
		}
		return hotelList;
	}
	
	@Override
	public int saveHotel(PostHotelVO hotel) {
		AddressDO addressDO = new AddressDO(hotel.getCityOfAddress(), hotel.getDistrictOfAddress(),
				hotel.getStreetOfAddress());
		
		HotelDO hotelDO = new HotelDO(hotel.getName(), hotel.getDescription(), addressDO, hotel.getContact(),
				hotel.getTelephone(), hotel.getEmail(), "");
		boolean isCreated = true;
		if (hotel.getId() != -1) {
			isCreated = false;
			HotelDO findedHotelDO = hotelDAO.findOne(hotel.getId());
			if(findedHotelDO == null){
				return -1;
			}
			addressDO.setId(findedHotelDO.getAddress().getId());
			hotelDO.setId(hotel.getId());
			hotelDO.setPictures(findedHotelDO.getPictures());
		}
		HotelDO savedHotel = hotelDAO.save(hotelDO);
		int hotelId = savedHotel.getId();
		if (isCreated) {
			hotelRatingDAO.save(new HotelRatingDO(hotelId, 0.0, 0, 5.0));
		}
		return hotelId;
	}

	@Override
	@Transactional
	public int addBanquetHall(PostBanquetHallVO banquetHallVO, int hotelId) {
		BanquetHallDO banquetHallDO = new BanquetHallDO();
		BeanUtils.copyProperties(banquetHallVO, banquetHallDO, "id");
		HotelDO hotel = hotelDAO.findOne(hotelId);
		if(hotel==null)
			return -1;
		updateTableRange(hotel, banquetHallDO.getMinimumTables(), banquetHallDO.getMaximumTables());
		banquetHallDO.setHotelDO(hotel);
		banquetHallDAO.save(banquetHallDO);
		return banquetHallDO.getId();
	}

	@Override
	public int addFeast(PostFeastVO feastVO, int hotelId) {
		FeastDO feastDO = new FeastDO();
		BeanUtils.copyProperties(feastVO, feastDO, "id");
		HotelDO hotel = hotelDAO.findOne(hotelId);
		if(hotel==null)
			return -1;
		updatePriceRange(hotel, feastDO.getPrice(), feastDO.getPrice());
		feastDO.setHotelDO(hotel);
		feastDAO.save(feastDO);
		return feastDO.getId();
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
				.map(banquetHallDO -> new SimplifiedBanquetHallDTO(banquetHallDO.getId(), banquetHallDO.getName(), banquetHallDO.getArea(),
						new int[] { banquetHallDO.getMinimumTables(), banquetHallDO.getMaximumTables() },
						banquetHallDO.getMinimumPrice(),
						banquetHallDO.getPicturesAsArray().length>0?
								ResourcesUriBuilder.buildUri(banquetHallDO.getPicturesAsArray()[0]):""))
				.collect(Collectors.toList());
		
		String[] pictures = ResourcesUriBuilder.buildUris(hotel.getPicturesAsArray());
		AddressDTO address = new AddressDTO();
		BeanUtils.copyProperties(hotel.getAddress(), address);
		return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getDescription(), 
				address,hotel.getContact(), hotel.getTelephone(), hotel.getEmail(),
				new int[]{hotel.getMinimumTables()==Integer.MAX_VALUE?0:hotel.getMinimumTables(), hotel.getMaximunTables()==-1?0: hotel.getMaximunTables()},
				new double[]{hotel.getMinimumPrice()==Double.MAX_VALUE?0:hotel.getMinimumPrice(), hotel.getMaximumPrice()==-1?0:hotel.getMaximumPrice()},
				getHotelRating(hotel.getId()), banquetHalls, feasts, pictures);
	}

	@Override
	public double getHotelRating(int id) {
		if(hotelDAO.findOne(id) == null){
			return -1;
		}
		HotelRatingDO rating = hotelRatingDAO.findOne(id);
		if(rating==null){
			hotelRatingDAO.save(new HotelRatingDO(id, 0.0, 0, 5.0));
			return 5.0;
		}
		return rating.getRating();
	}

	@Override
	public boolean deleteHotel(int id) {
		HotelDO hotel = hotelDAO.findOne(id);
		if(hotel!=null){
			storageService.delete(hotel.getPicturesAsArray());
			hotelDAO.delete(id);
		}
		return true;
	}
	
	@Override
	@Transactional
	public double updateHotelRating(int userId, int hotelId, double score) {
		if(hotelDAO.findOne(hotelId) == null){
			return -1;
		}
		if(hasUserRatedHotel(userId, hotelId)){
			return hotelRatingDAO.findOne(hotelId).getRating();
		}
		hotelRatingRecordDAO.save(new HotelRatingRecordDO(userId, hotelId));
		HotelRatingDO rating = hotelRatingDAO.findOne(hotelId);
		if(rating==null){
			rating = hotelRatingDAO.save(new HotelRatingDO(hotelId, 0, 0, 5.0));
		}
		rating.setRating((rating.getTotalScore()+score)/(rating.getTimes()+1));
		rating.setTimes(rating.getTimes()+1);
		rating.setTotalScore(rating.getTotalScore()+score);
		hotelRatingDAO.save(rating);
		return rating.getRating();
	}

	@Override
	public boolean hasUserRatedHotel(int userId, int hotelId) {
		if(hotelRatingRecordDAO.findByUserIdAndHotelId(userId, hotelId)!=null){
			return false;
		}
		return true;
	}

	@Override
	public String[] savePictures(int hotelId, PostFilesVO files) {
		HotelDO hotel = hotelDAO.findOne(hotelId);
		if(hotel==null)
			return null;
		MultipartFile[] mfs = files.getFiles();
		String[] savedFilenames = new String[mfs.length];
		for(int i=0;i<mfs.length;i++){
			savedFilenames[i] = storageService.store(mfs[i], UUIDNamingStrategy.generateUUID());
		}
		String pictures = DelimiterUtils.joinArray(savedFilenames, DelimiterUtils.PICTURE_DELIMITER);
		hotelDAO.addPictures(hotelId, pictures);
		return ResourcesUriBuilder.buildUris(savedFilenames);
	}

	@Override
	public boolean[] deletePictures(int hotelId, FileDeletionVO fileDeletionVO) {
		HotelDO hotel = hotelDAO.findOne(hotelId);
		if(hotel==null)
			return null;
		String pictures = hotel.getPictures();
		String[] files = fileDeletionVO.getNames();
		boolean[] rs = new boolean[files.length];
		for(int i=0;i<files.length;i++){
			rs[i] = storageService.delete(files[i]);
			pictures = pictures.replace(files[i]+DelimiterUtils.PICTURE_DELIMITER, "");
		}
		hotel.setPictures(pictures);
		hotelDAO.save(hotel);
		return rs;
	}

	@Override
	public boolean deleteHotels(EntityDeletionVO entityDeletionVO) {
		new ArrayList<>();
		List<HotelDO> hotels = new LinkedList<>();
		List<String> pictures = new LinkedList<>();
		hotelDAO.findAll(entityDeletionVO.getIds()).forEach(h->{
			pictures.addAll(Arrays.asList(h.getPicturesAsArray()));
			hotels.add(h);
		});
		hotelDAO.delete(hotels);
		storageService.delete(pictures);
		return true;
	}

	@Override
	public void updateTableRange(HotelDO hotel, int minimumTables, int maximumTables) {
		boolean isHotelUpdated = false;
		if(hotel.getMinimumTables()>minimumTables){
			hotel.setMinimumTables(minimumTables);
			isHotelUpdated = true;
		}
		if(hotel.getMaximunTables()<maximumTables){
			hotel.setMaximunTables(maximumTables);
			isHotelUpdated = true;
		}
		if(isHotelUpdated){
			hotelDAO.save(hotel);
		}
	}

	@Override
	public void updatePriceRange(HotelDO hotel, double minimumPrice, double maximumPrice) {
		boolean isHotelUpdated = false;
		if(hotel.getMinimumPrice()>minimumPrice){
			hotel.setMinimumPrice(minimumPrice);
			isHotelUpdated = true;
		}
		if(hotel.getMaximumPrice()<maximumPrice){
			hotel.setMaximumPrice(maximumPrice);
			isHotelUpdated = true;
		}
		if(isHotelUpdated){
			hotelDAO.save(hotel);
		}
	}

	@Override
	@Async
	@Transactional
	public void reCalculateTableRange(int hotelId) {
		HotelDO hotel = hotelDAO.findOne(hotelId);
		if(hotel==null)
			return;
		boolean[] hasUpdated = {false};
		int[] tableRange = {hotel.getMinimumTables(), hotel.getMaximunTables()};
		hotel.getBanquetHalls().forEach(bh->{
			if(tableRange[0]>bh.getMinimumTables()){
				tableRange[0] = bh.getMinimumTables();
				hasUpdated[0] = true;
			}
			if(tableRange[1]<bh.getMaximumTables()){
				tableRange[1] = bh.getMaximumTables();
				hasUpdated[0] = true;
			}
		});
		if(hasUpdated[0]){
			hotel.setMinimumTables(tableRange[0]);
			hotel.setMaximunTables(tableRange[1]);
			hotelDAO.save(hotel);
		}
	}

	@Override
	@Async
	public void reClaculatePriceRange(int hotelId) {
		HotelDO hotel = hotelDAO.findOne(hotelId);
		if(hotel==null)
			return;
		boolean[] hasUpdated = {false};
		double[] priceRange = {hotel.getMinimumPrice(), hotel.getMaximumPrice()};
		hotel.getFeasts().forEach(f->{
			if(priceRange[0]>f.getPrice()){
				hasUpdated[0] = true;
				priceRange[0] = f.getPrice();
			}
			if(priceRange[1]<f.getPrice()){
				hasUpdated[0] = true;
				priceRange[1] = f.getPrice();
			}
		});
		if(hasUpdated[0]){
			hotel.setMinimumPrice(priceRange[0]);
			hotel.setMaximumPrice(priceRange[1]);
			hotelDAO.save(hotel);
		}
	}
}
