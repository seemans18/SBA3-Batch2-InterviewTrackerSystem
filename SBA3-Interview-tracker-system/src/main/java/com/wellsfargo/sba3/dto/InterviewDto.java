package com.wellsfargo.sba3.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wellsfargo.sba3.entity.User;


public class InterviewDto {

	private Integer interviewId;
	
	private String interviewerName;

	private String interviewName;
	
	private String userSkills;
	
	private LocalTime time;

	private LocalDate date;
	
	private String interviewStatus;
	
	private String remarks;
	
	private List<UserDto> attendees;

	
	public InterviewDto() {

	}

	public InterviewDto(Integer interviewId, String interviewerName, String interviewName, String userSkills,
			LocalTime time, LocalDate date, String interviewStatus, String remarks, List<UserDto> attendees) {
		super();
		this.interviewId = interviewId;
		this.interviewerName = interviewerName;
		this.interviewName = interviewName;
		this.userSkills = userSkills;
		this.time = time;
		this.date = date;
		this.interviewStatus = interviewStatus;
		this.remarks = remarks;
		this.attendees = attendees;
	}

	public List<UserDto> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<UserDto> attendees) {
		this.attendees = attendees;
	}

	public String getInterviewerName() {
		return interviewerName;
	}

	public Integer getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Integer interviewId) {
		this.interviewId = interviewId;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getInterviewName() {
		return interviewName;
	}

	public void setInterviewName(String interviewName) {
		this.interviewName = interviewName;
	}

	public String getUserSkills() {
		return userSkills;
	}

	public void setUserSkills(String userSkills) {
		this.userSkills = userSkills;
	}

	public String getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(String interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "InterviewDto [interviewId=" + interviewId + ", interviewerName=" + interviewerName + ", interviewName="
				+ interviewName + ", userSkills=" + userSkills + ", time=" + time + ", date=" + date
				+ ", interviewStatus=" + interviewStatus + ", remarks=" + remarks + ", attendees=" + attendees + "]";
	}
	
}
