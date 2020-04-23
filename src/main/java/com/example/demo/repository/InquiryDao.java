package com.example.demo.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Inquiry;

@Repository
public interface InquiryDao {
	
	void insertInquiry(Inquiry inquiry);
	
//	int updateInquiry(Inquiry inquiry);
	
	List<Inquiry> getAll();
}