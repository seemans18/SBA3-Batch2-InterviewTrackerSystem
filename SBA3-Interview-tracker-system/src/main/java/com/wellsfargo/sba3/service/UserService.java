package com.wellsfargo.sba3.service;

import java.util.List;

import com.wellsfargo.sba3.dto.UserDto;
import com.wellsfargo.sba3.entity.Interview;
import com.wellsfargo.sba3.entity.User;
import com.wellsfargo.sba3.exception.InterviewException;
import com.wellsfargo.sba3.exception.UserException;

public interface UserService {

	UserDto addUser(UserDto user) throws UserException;
	//User saveUser(User user) throws UserException;
	
	String deleteUser(int userId) throws UserException, InterviewException;
	
	List<UserDto> getAllUsers() throws UserException;
	
	User getUser(int userId) throws UserException;
	
	boolean existsById(int userId) throws UserException;
	
	UserDto convertToUserDto(User user);

	User convertToUserEntity(UserDto userDto);
}
