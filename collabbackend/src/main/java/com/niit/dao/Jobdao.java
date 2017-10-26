package com.niit.dao;

import java.util.List;

import com.niit.model.AppliedJob;
import com.niit.model.Job;

public interface Jobdao {
	void saveJob(Job job);
	List<Job> getalljobs();
	Job getjobbyid(int id);
	void applyJob(AppliedJob applyingjob);
	List<AppliedJob> getappliedjobsbyuser(String username);
	List<AppliedJob> getappliedjobs();
	boolean isjobapplied(String username,int jobid);
}
