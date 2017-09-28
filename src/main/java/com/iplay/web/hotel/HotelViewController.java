package com.iplay.web.hotel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotelViewController {
	
	@Value("${iplay.view.templateFolder}")
	private String rootFolder;
	
	@GetMapping("/FeastBooking/hotels")
	public String indexView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/FeastBooking/hotel/*/info")
	public String hotelInfoView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/FeastBooking/hotel/*/banquet")
	public String hotelBanquetView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/partialView/hotels")
	public String hotelListPartialView() {
		return rootFolder + "hotel_list.html";
	}
	
	@GetMapping("/partialView/hotelDetail")
	public String hotelPartialView() {
		return rootFolder + "hotel_detail.html";
	}
	
	@GetMapping("/partialView/newHotel")
	public String createHotelPartialVIew() {
		return rootFolder + "create_hotel.html";
	}
	
	@GetMapping("/partialView/hotelDetailInfo")
	public String hotelDetailPartialView() {
		return rootFolder + "hotel_detail_info.html";
	}
	
	@GetMapping("partialView/hotelDetailBanquet")
	public String hotelDetailBanquetView() {
		return rootFolder + "hotel_detail_banquet.html";
	}

}
