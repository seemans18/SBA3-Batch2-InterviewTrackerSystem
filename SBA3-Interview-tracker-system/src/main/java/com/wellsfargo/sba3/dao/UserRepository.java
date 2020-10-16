package com.wellsfargo.sba3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.sba3.entity.User;
import com.wellsfargo.sba3.exception.UserException;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	boolean existsByMobile(String mobile);
	
	boolean existsByEmail(String email);
	
}
