package com.iplay.dao.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iplay.entity.user.Role;
import com.iplay.entity.user.UserDO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTest {
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void testInset() {
		UserDO user = userDAO.save(new UserDO("ivan3", "123456", "xiweipen@sina.com3", Role.USER));
		System.out.println(user.getId());
	}
}
