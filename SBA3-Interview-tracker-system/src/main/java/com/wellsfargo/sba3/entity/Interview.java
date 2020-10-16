package com.wellsfargo.sba3.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="interviews")
public class Interview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer interviewId;
	
	@Column(name="interviewerName")
	private String interviewerName;
	
	@Column(name="interviewName")
	private String interviewName;
	
	@Column(name="userSkills")
	private String userSkills;
		
	@Column(name="time")
	private LocalTime interviewTime;
	
	@Column(name="date")
	private Date interviewDate;
	
	@Column(name="interviewStatus")
	private String interviewStatus;
	
	@Column(name="remarks")
	private String remarks;
	

	@ManyToMany(fetch = FetchType.LAZY
				/*cascade = {
			        CascadeType.PERSIST,
			        CascadeType.MERGE
			    }*/)
			@JoinTable(name="user_interview", 
			   joinColumns= { 
					   @JoinColumn(name="interviewId", referencedColumnName ="interviewId"),
				}, 
			    inverseJoinColumns= @JoinColumn(name="userId", referencedColumnName ="userId"))
	private List<User> attendees;

	public Integer getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Integer interviewId) {
		this.interviewId = interviewId;
	}

	public String getInterviewerName() {
		return interviewerName;
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

	public LocalTime getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(LocalTime interviewTime) {
		this.interviewTime = interviewTime;
	}

	public Date getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	public List<User> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<User> attendees) {
		this.attendees = attendees;
	}

	@Override
	public String toString() {
		return "Interview [interviewId=" + interviewId + ", interviewerName=" + interviewerName + ", interviewName="
				+ interviewName + ", userSkills=" + userSkills + ", interviewStatus=" + interviewStatus + ", remarks="
				+ remarks + ", interviewTime=" + interviewTime + ", interviewDate=" + interviewDate + ", attendees="
				+ attendees + "]";
	}
	
	
}
