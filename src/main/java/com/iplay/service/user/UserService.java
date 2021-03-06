package com.iplay.service.user;

import java.util.Optional;

import com.iplay.entity.user.SimplifiedUserDO;
import com.iplay.entity.user.UserDO;

public interface UserService {

	Optional<UserDO> createOrdinaryUser(String email, String username, String password);
	
	Optional<UserDO> createAdministrator(String username, String password);
	
	Optional<UserDO> createManager(String username, String password);
	
	boolean isUsernameOccupied(String username);
	
	boolean isEmailOccupied(String email);
	
	Optional<SimplifiedUserDO> findSimplifiedUserByUsername(String username);
}
