package com.niit.controller;

import java.util.Date;
import java.util.Iterator;
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

import com.niit.dao.BlogPostdao;
import com.niit.dao.Userdao;
import com.niit.model.BlogPost;
import com.niit.model.Error;
import com.niit.model.User;

@Controller
public class BlogPostController {
	
	@Autowired
	private Userdao userdao;
	
	@Autowired
	private BlogPostdao blogpostdao;
	
	@RequestMapping(value="/saveBlogpost" , method=RequestMethod.POST)
	public ResponseEntity<?> saveblogpost(@RequestBody BlogPost blogpost,HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username=(String) session.getAttribute("username");
		User user=userdao.getUserbyUsername(username);
		try{
			Date date=new Date();
			blogpost.setPostedOn(date);
			blogpost.setPostedby(user);
			blogpostdao.saveblogpost(blogpost);
			return new ResponseEntity<BlogPost>(blogpost,HttpStatus.OK);
		}
		catch(Exception e){
			Error error=new Error(7,"Unable to enter blog details");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getAllblogpost/{approved}" , method=RequestMethod.GET)
	public ResponseEntity<?> getallblogpost(@PathVariable int approved,HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogposts=blogpostdao.getallblogposts(approved);
		return new ResponseEntity<List<BlogPost>>(blogposts,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getBlogpostByid/{id}" , method=RequestMethod.GET)
	public ResponseEntity<?> getblogpostbyid(@PathVariable int id,HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		BlogPost blogpost=blogpostdao.getblogpostbyid(id);
		return new ResponseEntity<BlogPost>(blogpost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateBlogpost" , method=RequestMethod.PUT)
	public ResponseEntity<?> updateblogpost(@RequestBody BlogPost blogpost,HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		try{
			if(!blogpost.isApproved() && blogpost.getRejectionReason()==null)
				blogpost.setRejectionReason("Not mentioned");
			blogpostdao.updateblogpost(blogpost);
			return new ResponseEntity<BlogPost>(blogpost,HttpStatus.OK);
		}
		catch(Exception e){
			Error error=new Error(7,"Unable to enter blog details");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value="/getBlogpostapproved" , method=RequestMethod.GET)
	public ResponseEntity<?> getblogpostapproved(HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username=(String) session.getAttribute("username");
		List<BlogPost> blogposts=blogpostdao.getapprovedblogposts(username);
		return new ResponseEntity<List<BlogPost>>(blogposts,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateviewedstatus" , method=RequestMethod.PUT)
	public ResponseEntity<?> updateviewedstatus(@RequestBody List<BlogPost> blogposts,HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
			for (BlogPost blog:blogposts) {
					blog.setViewedStatus(true);
					blogpostdao.updateblogpost(blog);
			}
			return new ResponseEntity<List<BlogPost>>(blogposts,HttpStatus.OK);
		
	}
	@RequestMapping(value="/getBlogpostByuserid/{id}" , method=RequestMethod.GET)
	public ResponseEntity<?> getblogpostbyuserid(@PathVariable String id,HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogposts=blogpostdao.getablogpostsbyuserid(id);
		return new ResponseEntity<List<BlogPost>>(blogposts,HttpStatus.OK);
	}
}
