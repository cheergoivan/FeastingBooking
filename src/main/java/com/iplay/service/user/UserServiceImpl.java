package com.iplay.service.user;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iplay.dao.user.UserDAO;
import com.iplay.entity.user.Role;
import com.iplay.entity.user.UserDO;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public Optional<UserDO> createOrdinaryUser(String email, String username, String password) {
		UserDO user = null;
		try{
			user = userDAO.save(new UserDO(username, encoder.encode(password), email, Role.USER));
		}catch(DataIntegrityViolationException e){
			LOGGER.error(e.getMessage());
		}
		return Optional.ofNullable(user);
	}

	@Override
	public boolean isUsernameOccupied(String username) {
		return userDAO.findOneByUsername(username)!=null;
	}

	@Override
	public boolean isEmailOccupied(String email) {
		return userDAO.findOneByEmail(email)!=null;
	}

	@Override
	public Optional<UserDO> createAdministrator(String username, String password) {
		UserDO user = null;
		try{
			user = userDAO.save(new UserDO(username, encoder.encode(password), username+"@FeastBooking.com", Role.ADMIN));
		}catch(DataIntegrityViolationException e){
			LOGGER.error(e.getMessage());
		}
		return Optional.ofNullable(user);
	}
}
