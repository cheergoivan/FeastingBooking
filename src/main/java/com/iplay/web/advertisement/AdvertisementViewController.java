package com.iplay.web.advertisement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdvertisementViewController {

	@Value("${iplay.view.templateFolder}")
	private String rootFolder;
	
	@GetMapping("/FeastBooking/advertisement")
	public String advertisementView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/partialView/advertisement")
	public String advertisementPartialView() {
		return rootFolder + "partialView_advertisement.html";
	}
	
}
