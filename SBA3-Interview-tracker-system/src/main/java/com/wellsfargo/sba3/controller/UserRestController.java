package com.wellsfargo.sba3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.sba3.dto.UserDto;
import com.wellsfargo.sba3.entity.User;
import com.wellsfargo.sba3.exception.InterviewException;
import com.wellsfargo.sba3.exception.UserException;
import com.wellsfargo.sba3.service.UserService;


@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto user) throws UserException{
		return new ResponseEntity<UserDto>(userService.addUser(user),HttpStatus.OK);
	} 
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers() throws UserException{
		return new ResponseEntity<List<UserDto>>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int userId) throws UserException, InterviewException{
		return new ResponseEntity<String>(userService.deleteUser(userId),HttpStatus.OK);
	}

}
