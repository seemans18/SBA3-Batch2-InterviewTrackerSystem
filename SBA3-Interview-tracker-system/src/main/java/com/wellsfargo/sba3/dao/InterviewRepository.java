package com.wellsfargo.sba3.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.sba3.entity.Interview;
@Repository
public interface InterviewRepository extends JpaRepository<Interview, Integer>{
	
	Interview findById(int interviewId) ;
	
	List<Interview> findAllByInterviewName(String interviewName);
	
	List<Interview> findAllByInterviewerName(String interviewerName);
	
	/*@Query("SELECT COUNT(*) FROM interviews")
	int totalNoOfInteviews();*/

}
