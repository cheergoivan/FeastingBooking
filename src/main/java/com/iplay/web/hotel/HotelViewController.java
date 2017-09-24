package com.iplay.web.hotel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotelViewController {
	
	@Value("${iplay.view.templateFolder}")
	private String rootFolder;
	
	@GetMapping("/hotels")
	public String indexView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/partialView/hotels")
	public String productListPartialView() {
		return rootFolder + "hotel_list.html";
	} 

}
