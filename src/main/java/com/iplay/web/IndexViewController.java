package com.iplay.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexViewController {

	@Value("${iplay.view.templateFolder}")
	private String templateFolder;
	
	@GetMapping("/partialView/FeastBooking")
	public String index() {
		return templateFolder + "feastBookingIndex.html";
	}
}
