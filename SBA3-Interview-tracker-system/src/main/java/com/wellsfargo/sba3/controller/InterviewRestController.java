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

import com.wellsfargo.sba3.dto.InterviewDto;
import com.wellsfargo.sba3.dto.UserDto;
import com.wellsfargo.sba3.entity.User;
import com.wellsfargo.sba3.exception.InterviewException;
import com.wellsfargo.sba3.exception.UserException;
import com.wellsfargo.sba3.service.InterviewService;
import com.wellsfargo.sba3.service.UserService;


@RestController
@RequestMapping("/interviews")
public class InterviewRestController {

	@Autowired
	private InterviewService interviewService;

	@PostMapping
	public ResponseEntity<InterviewDto> addInterview(@RequestBody InterviewDto interviewDto) throws InterviewException, UserException{
		return new ResponseEntity<InterviewDto>(interviewService.addInterview(interviewDto),HttpStatus.OK);
	} 
	
	@GetMapping
	public ResponseEntity<List<InterviewDto>> getAllInterviews() throws InterviewException{
		return new ResponseEntity<List<InterviewDto>>(interviewService.getAllInterviews(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteInterview(@PathVariable("id") int interviewId) throws InterviewException{
		interviewService.deleteInterview(interviewId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/count")
	public ResponseEntity<String> getByInterviewCount() throws InterviewException{
		return new ResponseEntity<String>(interviewService.getInterviewCount(),HttpStatus.OK);
	}
	
	@GetMapping("/interviewName/{interviewName}")
	public ResponseEntity<List<InterviewDto>> getByInterviewName(@PathVariable("interviewName") String interviewName) throws InterviewException{
		return new ResponseEntity<List<InterviewDto>>(interviewService.getByInterviewName(interviewName),HttpStatus.OK);
	}
	
	@GetMapping("/interviewerName/{interviewerName}")
	public ResponseEntity<List<InterviewDto>> getInterviewerName(@PathVariable("interviewerName") String interviewerName) throws InterviewException{
		return new ResponseEntity<List<InterviewDto>>(interviewService.getByInterviewerName(interviewerName),HttpStatus.OK);
	}
	
	@PutMapping("updateStatus/{interviewID}/{status}")
	public ResponseEntity<InterviewDto> updateInterviewStatus(@PathVariable("interviewID") int interviewID,@PathVariable("status") String status) throws InterviewException{
		return new ResponseEntity<InterviewDto>(interviewService.updateInterviewStatus(interviewID, status),HttpStatus.OK);
	}
	
	@PutMapping("addAttendee/{interviewID}/{userId}")
	public ResponseEntity<InterviewDto> addAttendee(@PathVariable("interviewID") int interviewID,@PathVariable("userId") int userId) throws InterviewException, UserException{
		return new ResponseEntity<InterviewDto>(interviewService.addAttendeeToInterview(interviewID, userId),HttpStatus.OK);
	}
	
	@GetMapping("/showAttendees/{interviewID}")
	public ResponseEntity<List<UserDto>> getAllAttendees(@PathVariable("interviewID") int interviewID) throws InterviewException{
		return new ResponseEntity<List<UserDto>>(interviewService.getAllAttendees(interviewID),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{interviewId}/{userId}")
	public ResponseEntity<Void> deleteAttendeeFromAnInterview(@PathVariable("interviewId") int interviewId,@PathVariable("userId") int userId) throws InterviewException, UserException{
		interviewService.deleteAttendee(interviewId,userId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
