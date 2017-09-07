package com.iplay.service.auth;

import java.util.Optional;

import com.iplay.entity.user.UserDO;

public interface AuthService {
	
	Optional<UserDO> authenticate(String principal, String password);
	
}
