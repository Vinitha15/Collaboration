package com.niit.controller;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.Jobdao;
import com.niit.dao.Userdao;
import com.niit.model.AppliedJob;
import com.niit.model.Error;
import com.niit.model.Job;
import com.niit.model.User;

@Controller
public class JobController {
	@Autowired
	private Userdao userdao;
	
	@Autowired
	private Jobdao jobdao;
	
	
	@RequestMapping(value="/saveJob", method=RequestMethod.POST)
	public ResponseEntity<?> savejob(@RequestBody Job job,HttpSession session){
		
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username=(String) session.getAttribute("username");
		User user=userdao.getUserbyUsername(username);
		if(user.getRole().equals("ADMIN")){
			try{
				Date date=new Date();
				job.setPostedOn(date);
				jobdao.saveJob(job);
				return new ResponseEntity<Job>(job,HttpStatus.OK);
			}
			catch(Exception e){
				Error error=new Error(7,"Unable to enter job details");
				return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		else{
			Error error=new Error(6,"Access denied");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			
		}
	}
	
	@RequestMapping(value="/getAlljobs", method=RequestMethod.GET)
	public ResponseEntity<?> getalljobs(HttpSession session){
		if(session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<Job> jobs=jobdao.getalljobs();
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
		
	}

	@RequestMapping(value="/getjobByid/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getjobbyid(HttpSession session,@PathVariable int id){
		if(session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		Job job=jobdao.getjobbyid(id);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/applyJob", method=RequestMethod.POST)
	public ResponseEntity<?> applyjob(@RequestBody Job job,HttpSession session){
		
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username=(String) session.getAttribute("username");
		User user=userdao.getUserbyUsername(username);
		AppliedJob applyingjob= new AppliedJob();
		applyingjob.setUsers(user);
		applyingjob.setJobs(job);
		jobdao.applyJob(applyingjob);
		return new ResponseEntity<AppliedJob>(applyingjob,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllappliedjobs", method=RequestMethod.GET)
	public ResponseEntity<?> getallappliedjobs(HttpSession session){
		if(session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<AppliedJob> AppliedJobs=jobdao.getappliedjobs();
		return new ResponseEntity<List<AppliedJob>>(AppliedJobs,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getappliedJobs", method=RequestMethod.GET)
	public ResponseEntity<?> applyjob(HttpSession session){
		
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username=(String) session.getAttribute("username");
		List<AppliedJob> appliedjobs= jobdao.getappliedjobsbyuser(username);
		return new ResponseEntity<List<AppliedJob>>(appliedjobs,HttpStatus.OK);
	}
	 @RequestMapping(value="/isapplied",method=RequestMethod.PUT)
	    public ResponseEntity<?> getMutualFriends(@RequestBody List<Job> jobs,HttpSession session){
	    	String username=(String)session.getAttribute("username");
	    	if(username==null){
	    		Error error=new Error(5,"UnAuthorized Access..");
	    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	    	}
	    	Map<String, Boolean> isapplied=new HashMap<String, Boolean>();
	    	for(Job j :jobs){
	    		isapplied.put(j.getJobtitle(), jobdao.isjobapplied(username, j.getJobId()));
	    	System.out.println(isapplied.size());
	    	}
	    	System.out.println(isapplied);
	    	return new ResponseEntity<Map<String,Boolean>>(isapplied,HttpStatus.OK);
	    }
	
}
