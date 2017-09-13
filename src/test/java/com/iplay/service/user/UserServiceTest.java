package com.iplay.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	@Autowired
	private UserService userService;
	
	private static final String[] USERS = {"ivan1","ivan2"};
	
	private static final String[] ADMINISTRATORS = {"admin"}; 
	
	private static final String PASSWORD = "123456";
	
	@Test
	public void initializeUsers(){
		for(String user:USERS){
			if(!userService.isUsernameOccupied(user)){
				userService.createOrdinaryUser(user+"@FeastBooking.com", user, PASSWORD);
			}
		}
		for(String admin:ADMINISTRATORS){
			if(!userService.isUsernameOccupied(admin)){
				userService.createAdministrator(admin, PASSWORD);
			}
		}
		
	}
}
