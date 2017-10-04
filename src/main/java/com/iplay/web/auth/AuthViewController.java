package com.iplay.web.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

	@Value("${iplay.view.templateFolder}")
	private String templateFolder;
	
	@GetMapping("/signin")
	public String signin() {
		return templateFolder + "index.html";
	}
	
	@GetMapping("/partialView/signin")
	public String AdminSignin() {
		return templateFolder + "partialView_signin.html";
	}
	
}
