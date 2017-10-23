package com.iplay.web.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderViewController {

	@Value("${iplay.view.templateFolder}")
	private String rootFolder;
	
	@GetMapping("/partialView/orders")
	public String partialViewOrderList() {
		return rootFolder + "partialView_orders.html";
	}
	
	@GetMapping("/FeastBooking/orders")
	public String ordersView() {
		return rootFolder + "index.html";
	}
}
