package com.iplay.web.recommendation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecommendationViewController {

	@Value("${iplay.view.templateFolder}")
	private String rootFolder;
	
	@GetMapping("/FeastBooking/hotels/recommendation")
	public String recommendationView() {
		return rootFolder + "index.html";
	}
	
	@GetMapping("/partialView/hotel_recommendation")
	public String recommendationPartialView() {
		return rootFolder + "partialView_recommendation.html";
	}
	
}
