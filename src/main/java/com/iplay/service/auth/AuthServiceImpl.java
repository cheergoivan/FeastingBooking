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
		List<UserDO> users = userDAO.findByPrincipal(principal);
		if(users.size()>0){
			for(UserDO user:users){
				if(encoder.matches(password, user.getPassword()))
					return Optional.ofNullable(user); 
			}
		}
		return Optional.ofNullable(null);
	}
	
}
