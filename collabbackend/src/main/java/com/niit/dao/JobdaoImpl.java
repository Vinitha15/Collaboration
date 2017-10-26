package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.AppliedJob;
import com.niit.model.Job;

@Repository
@Transactional
public class JobdaoImpl implements Jobdao {
	@Autowired
	private SessionFactory sessionFactory;

	public void saveJob(Job job) {
		Session session=sessionFactory.getCurrentSession();
		session.save(job);

	}

	public List<Job> getalljobs() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Job");
		List<Job> jobs=query.list();
		return jobs;
	}

	public Job getjobbyid(int id) {
		Session session=sessionFactory.getCurrentSession();
		Job job=(Job) session.get(Job.class, id);
		return job;
	}

	public void applyJob(AppliedJob applyingjob) {
		Session session=sessionFactory.getCurrentSession();
		session.save(applyingjob);
		
	}

	public List<AppliedJob> getappliedjobsbyuser(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from AppliedJob where users.username=?");
		query.setString(0, username);
		List<AppliedJob> appliedjobs=query.list();
		return appliedjobs;
	}
	
	
	public List<AppliedJob> getappliedjobs() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from AppliedJob");
		List<AppliedJob> AppliedJobs=query.list();
		return AppliedJobs;
	}

	public boolean isjobapplied(String username, int jobid) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from AppliedJob where users.username=? and jobs.jobId=?");
		query.setString(0, username);
		query.setInteger(1, jobid);
		AppliedJob appliedjob=(AppliedJob) query.uniqueResult();
		if(appliedjob==null)
			return false;
		else
			return true;
	}

}
