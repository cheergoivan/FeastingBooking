package com.iplay.web.hotel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotelViewController {
	
	@Value("${iplay.view.templateFolder}")
	private String rootFolder;
	
	@GetMapping("/FeastBooking/hotels/*")
	public String indexView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/partialView/hotels")
	public String hotelsPartialView() {
		return rootFolder + "partialView_hotels.html";
	}
	
	@GetMapping("/partialView/hotel_list")
	public String hotelListPartialView() {
		return rootFolder + "partialView_hotel_list.html";
	}
	
	@GetMapping("/FeastBooking/hotel/*/info")
	public String hotelInfoView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/FeastBooking/hotel/*/banquet/*")
	public String hotelBanquetView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/partialView/hotel_detail")
	public String hotelPartialView() {
		return rootFolder + "partialView_hotel_detail.html";
	}
	
	@GetMapping("/partialView/newHotel")
	public String createHotelPartialVIew() {
		return rootFolder + "partialView_create_hotel.html";
	}
	
	@GetMapping("/partialView/hotelDetailInfo")
	public String hotelDetailPartialView() {
		return rootFolder + "partialView_hotel_info.html";
	}
	
	@GetMapping("/partialView/hotelDetailBanquet")
	public String hotelDetailBanquetView() {
		return rootFolder + "partialView_hotel_banquet.html";
	}

	@GetMapping("/partialView/newBanquet")
	public String hotelNewBanquetPartialView() {
		return rootFolder + "partialView_create_banquet.html";
	}
	
	@GetMapping("/FeastBooking/hotel/*/newBanquet")
	public String hotelNewBanquetView() {
		return rootFolder + "index.html";
	}
}
