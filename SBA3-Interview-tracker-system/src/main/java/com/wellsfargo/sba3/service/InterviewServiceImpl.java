package com.wellsfargo.sba3.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.sba3.dao.InterviewRepository;
import com.wellsfargo.sba3.dto.InterviewDto;
import com.wellsfargo.sba3.dto.UserDto;
import com.wellsfargo.sba3.entity.Interview;
import com.wellsfargo.sba3.entity.User;
import com.wellsfargo.sba3.exception.InterviewException;
import com.wellsfargo.sba3.exception.UserException;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private InterviewRepository interviewRepo;
	
	@Autowired
	private UserService userService;
	
	//validations for Interview details
	private boolean isValidInterviewDetails(InterviewDto interviewDto) throws InterviewException {
		boolean isValid = true;
		List<String> errMsgs = new ArrayList<>();
		
		if(interviewDto!=null) {
			String interviewerName = interviewDto.getInterviewerName();
			if(!(interviewerName != null && interviewerName.length()>=5 && interviewerName.length()<=30)) {
				isValid=false;
				errMsgs.add("Interviewer name cannot be left blank and must be of length between 5 and 30 characters");
			}
			
			String interviewName = interviewDto.getInterviewName();
			if(!(interviewName != null && interviewName.length()>=3 && interviewName.length()<=30)) {
				isValid=false;
				errMsgs.add("Interview name cannot be left blank and must be of length between 3 and 30 characters");
			}
			
			String userSkills = interviewDto.getUserSkills();
			if(!(userSkills != null && userSkills.length()>=5 && userSkills.length()<=30)) {
				isValid=false;
				errMsgs.add("User Skills cannot be left blank and must be of length between 5 and 30 characters");
			}
			
			String interviewStatus = interviewDto.getInterviewStatus();
			if(!(interviewStatus != null && interviewStatus.length()>=5 && interviewStatus.length()<=100)) {
				isValid=false;
				errMsgs.add("Interview Status cannot be left blank and must be of length between 5 and 100 characters");
			}
			
			String remarks = interviewDto.getInterviewStatus();
			if(!isValidRemark(remarks)) {
				isValid=false;
				errMsgs.add("Remarks cannot be left blank and must be of length between 5 and 100 characters");
			}
			
			if(!errMsgs.isEmpty()) {
				throw new InterviewException("Invalid Interview details: "+errMsgs);
				}
			}else {
				isValid=false;
				throw new InterviewException("Interview details are not supplied");
			}

		return isValid;	
	}
	
	private boolean isValidRemark(String remarks) {
		return remarks != null && remarks.length()>=5 && remarks.length()<=100;
	}
	
	@Override
	@Transactional
	public InterviewDto addInterview(InterviewDto interviewDto) throws InterviewException, UserException {
		if(isValidInterviewDetails(interviewDto)) {
			if(interviewDto.getDate() == null){
				interviewDto.setDate(LocalDate.now());
			}
			if(interviewDto.getTime() == null){
				interviewDto.setTime(LocalTime.now());
			}
			
			Interview interview = new Interview();
			
			interview.setInterviewerName(interviewDto.getInterviewerName());
			interview.setInterviewName(interviewDto.getInterviewName());
			interview.setUserSkills(interviewDto.getUserSkills());
			interview.setInterviewDate(Date.valueOf(interviewDto.getDate()));
			interview.setInterviewTime(interviewDto.getTime());
			interview.setInterviewStatus(interviewDto.getInterviewStatus());
			interview.setRemarks(interviewDto.getRemarks());
			
			if(interviewDto.getAttendees() != null) {
				List<UserDto> attendeeDto = interviewDto.getAttendees();
				List<User> attendees = new ArrayList<>();
				for(UserDto uDto:attendeeDto) {
					if(! userService.existsById(uDto.getUserId())) {
						throw new UserException("User with the Id "+ uDto.getUserId() +" doesnt exist");
					}
					for(User u1:attendees) {
						if(u1.getUserId() == uDto.getUserId()) {
							throw new InterviewException("User with the Id "+ uDto.getUserId() +" is duplicated while adding to the interview");
						}
					}
					
					attendees.add(userService.convertToUserEntity(uDto));
				}
				interview.setAttendees(attendees);
			}
			interviewRepo.save(interview);
			interviewDto.setInterviewId(interview.getInterviewId());
		}
		return interviewDto;
	}

	@Override
	public boolean deleteInterview(int interviewId) throws InterviewException {
		if(!interviewRepo.existsById(interviewId)) {
			throw new InterviewException("Interview with the Id "+ interviewId +" doesn't exist!!");
		}
		interviewRepo.deleteById(interviewId);
		return true;
	}

	@Override
	@Transactional
	public InterviewDto updateInterviewStatus(int interviewId, String interviewStatus) throws InterviewException {
		if(interviewRepo.existsById(interviewId)) {
			Interview interview = new Interview();
			interview = interviewRepo.findById(interviewId);
			interview.setInterviewStatus(interviewStatus);
			
			return convertToDto(interview);
		}else {
			throw new InterviewException("Inteview with the id "+ interviewId + " doesnot exist");
		}
	}

	
	@Override
	public String getInterviewCount() throws InterviewException {
		long interviewCount = interviewRepo.count();
		return "Total number of Interviews present is "+ interviewCount;
	}

	@Override
	public List<InterviewDto> getAllInterviews() throws InterviewException {
		List<Interview> allInterviews = new ArrayList<>();
		allInterviews = interviewRepo.findAll();
		
		List<InterviewDto> allInterviewsDto = new ArrayList<>(); 
		for(Interview i:allInterviews) {
			//InterviewDto interviewDto = convertToDto(i);
			allInterviewsDto.add(convertToDto(i));
		}
		return allInterviewsDto; 
	}

	@Override
	@Transactional
	public InterviewDto addAttendeeToInterview(int interviewId, int userId) throws InterviewException, UserException {
		if(! interviewRepo.existsById(interviewId)) {
			throw new InterviewException("Inteview with the id "+ interviewId + " doesnot exist");
		}
		if(! userService.existsById(userId)) {
			throw new UserException("User with the id "+ userId + " doesnot exist");
		}
		
		Interview interview = interviewRepo.findById(interviewId);
		List<User> attendees = interview.getAttendees();
		for(User u:attendees) {
			if(u.getUserId()== userId)
				throw new InterviewException("User id: "+ userId + " is already associated with the Interview id: "+ interviewId);
		}
		//attendees.add(userService.getUser(userId));
		interview.getAttendees().add(userService.getUser(userId));
		//interview.setAttendees(attendees);
		interviewRepo.save(interview);
		
		return convertToDto(interview);
	}

	@Override
	public List<UserDto> getAllAttendees(int interviewId) throws InterviewException {
		if(! interviewRepo.existsById(interviewId)) {
			throw new InterviewException("Inteview with the id "+ interviewId + " doesnot exist");
		}
		Interview interview =interviewRepo.findById(interviewId);
		List<UserDto> attendeesDto = new ArrayList<>();
		for(User u : interview.getAttendees()) {
			attendeesDto.add(userService.convertToUserDto(u));
		}
		return attendeesDto;
	}

	@Override
	public List<InterviewDto> getByInterviewName(String interviewName) throws InterviewException {
		if(interviewRepo.findAllByInterviewName(interviewName).isEmpty()) {
			throw new InterviewException("Interview with the name "+ interviewName +" Not found");
		}	
		List<InterviewDto> interviewsByDto = new ArrayList<>();
		for(Interview i: interviewRepo.findAllByInterviewName(interviewName)) {
			interviewsByDto.add(convertToDto(i));
		}
		//return Optional.of(convertToDto(interviewRepo.findByInterviewName(interviewName))).orElse(null);
		//return convertToDto(interviewRepo.findByInterviewName(interviewName));
		return interviewsByDto;
	}

	@Override
	public List<InterviewDto> getByInterviewerName(String interviewerName) throws InterviewException {
		if(interviewRepo.findAllByInterviewerName(interviewerName).isEmpty()) {
			throw new InterviewException("Interview with the interviewer name "+ interviewerName +" Not found");
		}
		List<InterviewDto> interviewsByDto = new ArrayList<>();
		for(Interview i: interviewRepo.findAllByInterviewerName(interviewerName)) {
			interviewsByDto.add(convertToDto(i));
		}
		//return convertToDto(interviewRepo.findByInterviewerName(interviewerName));
		return interviewsByDto;
	}

	@Override
	public void deleteAttendee(int interviewId,int userId) throws InterviewException, UserException {
		//boolean isRemoved = false;
		if(! interviewRepo.existsById(interviewId)) {
			throw new InterviewException("Inteview with the id "+ interviewId + " doesnot exist");
		}
		if(! userService.existsById(userId)) {
			throw new UserException("User with the id "+ userId + " doesnot exist");
		}
		
		Interview interview =interviewRepo.findById(interviewId);
		List<User> attendees = interview.getAttendees();
		attendees.remove(userService.getUser(userId));
		interviewRepo.save(interview);
		return;
	}

	
	@Override
	public String deleteAttendee(int userId) throws InterviewException, UserException {
		
		String interviewIds = "";
		
		List<Interview> allInterviews = interviewRepo.findAll();
		for(Interview i : allInterviews) {
			List<User> attendees = i.getAttendees();
			//attendees.remove(userService.getUser(userId));
			//interviewRepo.save(i);
			Iterator<User> itr = attendees.iterator();
			while (itr.hasNext()) {
				   User u = itr.next();
				   if(u.getUserId() == userId) {
					   itr.remove();
					   interviewIds = interviewIds + " " + i.getInterviewId();
					   break;
				   }
				}	
			interviewRepo.save(i);
			}
		return interviewIds;
	}
		
	
	@Override
	public InterviewDto convertToDto(Interview interview){
		
		InterviewDto interviewDto = new InterviewDto();
		
		interviewDto.setInterviewId(interview.getInterviewId());
		interviewDto.setInterviewerName(interview.getInterviewerName());
		interviewDto.setInterviewName(interview.getInterviewName());
		interviewDto.setUserSkills(interview.getUserSkills());
		interviewDto.setTime(interview.getInterviewTime());
		interviewDto.setDate(interview.getInterviewDate().toLocalDate());
		interviewDto.setInterviewStatus(interview.getInterviewStatus());
		interviewDto.setRemarks(interview.getRemarks());
		
		List<User> attendees = interview.getAttendees();
		List<UserDto> attendeesDto = new ArrayList<>();
		for(User u:attendees) {
			attendeesDto.add(userService.convertToUserDto(u));
		}
		interviewDto.setAttendees(attendeesDto);
		return interviewDto;	
	}
	

}
