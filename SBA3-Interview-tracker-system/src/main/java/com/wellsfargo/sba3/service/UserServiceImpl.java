package com.wellsfargo.sba3.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.sba3.dao.UserRepository;
import com.wellsfargo.sba3.dto.InterviewDto;
import com.wellsfargo.sba3.dto.UserDto;
import com.wellsfargo.sba3.entity.Interview;
import com.wellsfargo.sba3.entity.User;
import com.wellsfargo.sba3.exception.InterviewException;
import com.wellsfargo.sba3.exception.UserException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private InterviewService interviewService;
	
	//ModelMapper modelMapper = new ModelMapper();
	
	//validations for User fields
	private boolean isValidFirstname(String fname) {
		return fname != null && fname.length()>=5 && fname.length()<=30;
	}
	
	private boolean isValidLastname(String lname) {
		return lname != null && lname.length()>=3 && lname.length()<=25;
	}
	
	private boolean isValidMobile(String mobile) {				
		return mobile!=null && mobile.length() == 10;
	}
	
	private boolean isValidEmail(String email) {				
		return email!=null && email.matches("[a-zA-Z][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
	}
	
	private boolean isValidUser(UserDto userDto) throws UserException {
		List<String> errMsgs = new ArrayList<>();
		boolean isValid=true;
		
		if(userDto!=null) {
			if(!isValidFirstname(userDto.getFirstName())) {
				isValid=false;
				errMsgs.add("First name cannot be left blank and must be of length between 5 and 30 characters");
			}
			if(!isValidLastname(userDto.getLastName())) {
				isValid=false;
				errMsgs.add("Last name cannot be left blank and must be of length between 3 and 25 characters");
			}
			
			if(!isValidMobile(userDto.getMobile())) {
				isValid=false;
				errMsgs.add("Mobile Number can not be left blank and must be of 10 digits only");
			}
			
			if(!isValidEmail(userDto.getEmail())) {
				isValid=false;
				errMsgs.add("Email ID can not be left blank and must be of valid pattern");
			}
			
			if(!errMsgs.isEmpty()) {
				throw new UserException("Invalid User details: "+errMsgs);
			}
		}else {
			isValid=false;
			throw new UserException("User details are not supplied");
		}
		
		return isValid;
	}
	
	@Override
	@Transactional
	public UserDto addUser(UserDto userDto) throws UserException {
		if(userRepo.existsByEmail(userDto.getEmail())) {
			throw new UserException("User with the email "+ userDto.getEmail() +" already exists!!");
		}
		
		if(userRepo.existsByMobile(userDto.getMobile())) {
			throw new UserException("User with mobile number "+ userDto.getMobile() +" already exists!!");
		}
		
		if(isValidUser(userDto)) {
			User user = new User();
			
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setMobile(userDto.getMobile());
			user.setEmail(userDto.getEmail());
			userRepo.save(user);
			userDto.setUserId(user.getUserId());
			
			/*User userEntity = new User();
			BeanUtils.copyProperties(userDto, userEntity);*/
			
			/*userDto = modelMapper.map(userEntity, UserDto.class);
			userRepo.save(userEntity);*/
		}
		return userDto;
	}

	@Override
	@Transactional
	public String deleteUser(int userId) throws UserException, InterviewException {
		String returnMsg = "";
		if(userRepo.existsById(userId)) {
			String interviewIds = interviewService.deleteAttendee(userId);
			userRepo.deleteById(userId);
			if(interviewIds.equalsIgnoreCase("")) {
				returnMsg = "User ID "+ userId + " has been deleted and the user was not an attendee in any of the Interviews";
			}else {
				returnMsg = "User ID "+ userId + " has been deleted from the Interviews [" + interviewIds +"]";
			}
		}else {
			throw new UserException("User Id "+ userId +" does not exist");
		}
		return returnMsg;
	}

	@Override
	public User getUser(int userId) throws UserException {
		return userRepo.findById(userId).get();
	}

	@Override
	public boolean existsById(int userId) throws UserException {
		return userRepo.existsById(userId);
	}

	@Override
	public List<UserDto> getAllUsers() throws UserException {
		
		List<User> allUsers = userRepo.findAll();
		
		List<UserDto> allUsersDto = new ArrayList<>(); 
		for(User u:allUsers) {
			//InterviewDto interviewDto = convertToDto(i);
			allUsersDto.add(convertToUserDto(u));
		}
		return allUsersDto; 
	}
	
	@Override
	public UserDto convertToUserDto(User user){
		
		UserDto userDto = new UserDto();
		
		userDto.setUserId(user.getUserId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setMobile(user.getMobile());
		
		/*List<Interview> interviews = user.getInterviews();
		List<InterviewDto> interviewsDto = new ArrayList<>();
		for(Interview i:interviews) {
			interviewsDto.add(interviewService.convertToDto(i));
		}
		userDto.setInterviews(interviewsDto);*/
		return userDto;	
	}
	
	@Override
	public User convertToUserEntity(UserDto userDto){
		
		User user = new User();
		
		user.setUserId(userDto.getUserId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setMobile(userDto.getMobile());
		return user;	
	}
}
