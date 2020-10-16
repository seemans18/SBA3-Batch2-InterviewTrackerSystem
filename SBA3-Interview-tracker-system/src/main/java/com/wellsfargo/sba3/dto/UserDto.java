package com.wellsfargo.sba3.dto;

import java.util.List;

public class UserDto {
	
	private Integer userId;
	
	private String firstName;
	
	private String lastName;
	
	private String mobile;
		
	private String email;
	
	private List<InterviewDto> interviews;
	
	public UserDto() {

	}

	public UserDto(Integer userId, String firstName, String lastName, String mobile, String email,
			List<InterviewDto> interviews) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobile = mobile;
		this.email = email;
		this.interviews = interviews;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/*public List<InterviewDto> getInterviews() {
		return interviews;
	}

	/*public void setInterviews(List<InterviewDto> interviews) {
		this.interviews = interviews;
	}*/

	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", mobile="
				+ mobile + ", email=" + email + ", interviews=" + interviews + "]";
	}

	
}
