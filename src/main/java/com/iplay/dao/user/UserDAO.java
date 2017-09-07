package com.iplay.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.iplay.entity.user.UserDO;

public interface UserDAO extends CrudRepository<UserDO,Integer>{
	
	@Query("SELECT u from #{#entityName} u WHERE (u.username = ?1 OR u.email = ?1) AND u.password = ?2")
	List<UserDO> findByPrincipalAndPassword(String principal, String password);
	
	UserDO findOneByUsername(String username);
	
	UserDO findOneByEmail(String email);
}
