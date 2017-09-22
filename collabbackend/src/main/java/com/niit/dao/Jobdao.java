package com.niit.dao;

import java.util.List;

import com.niit.model.Job;

public interface Jobdao {
	void saveJob(Job job);
	List<Job> getalljobs();
	Job getjobbyid(int id);
	
}
