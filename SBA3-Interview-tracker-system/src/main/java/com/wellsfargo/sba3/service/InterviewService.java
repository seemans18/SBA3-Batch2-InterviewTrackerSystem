package com.wellsfargo.sba3.service;

import java.util.List;

import com.wellsfargo.sba3.dto.InterviewDto;
import com.wellsfargo.sba3.dto.UserDto;
import com.wellsfargo.sba3.entity.Interview;
import com.wellsfargo.sba3.exception.InterviewException;
import com.wellsfargo.sba3.exception.UserException;

public interface InterviewService {
	
	//add
	InterviewDto addInterview(InterviewDto interview) throws InterviewException, UserException;
	
	//delete
	boolean deleteInterview(int interviewId) throws InterviewException;
	
	//update interview status
	InterviewDto updateInterviewStatus(int interviewId, String interviewStatus) throws InterviewException;
	
	//search with interview name
	List<InterviewDto> getByInterviewName(String interviewName) throws InterviewException;
	
	//search with interviewerName
	List<InterviewDto> getByInterviewerName(String interviewereName) throws InterviewException;
	
	//count
	String getInterviewCount() throws InterviewException;
	
	//display
	List<InterviewDto> getAllInterviews() throws InterviewException;
	
	//add- attendees
	InterviewDto addAttendeeToInterview(int interviewId, int userId) throws InterviewException, UserException;
	
	//show all attendees
	List<UserDto> getAllAttendees(int interviewId) throws InterviewException;
	
	//delete attendee from the particular interview
	void deleteAttendee(int interviewId,int userId) throws InterviewException, UserException;
	
	//delete attendee from the all the interviews
	String deleteAttendee(int userId) throws InterviewException, UserException;
		
	//convert to dto
	InterviewDto convertToDto(Interview interview);
}
