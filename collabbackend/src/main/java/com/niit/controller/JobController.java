package com.niit.controller;



import java.util.Date;
import java.util.List;

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

	
}
