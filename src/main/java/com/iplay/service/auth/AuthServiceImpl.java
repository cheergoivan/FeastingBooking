package com.iplay.service.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iplay.dao.user.UserDAO;
import com.iplay.entity.user.UserDO;

@Service
public class AuthServiceImpl implements AuthService{
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public Optional<UserDO> authenticate(String principal, String password) {
		List<UserDO> users = userDAO.findByPrincipalAndPassword(principal, encoder.encode(password));
		UserDO user = null;
		if(users.size()>0)
			user = users.get(0);
		return Optional.ofNullable(user);
	}
	
}
