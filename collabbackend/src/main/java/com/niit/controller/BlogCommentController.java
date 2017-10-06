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

import com.niit.dao.BlogPostdao;
import com.niit.dao.Userdao;
import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.Error;
import com.niit.model.User;

@Controller
public class BlogCommentController {
	@Autowired
	private Userdao userdao;
	
	@Autowired
	private BlogPostdao blogpostdao;
	
	@RequestMapping(value="/saveBlogcomment" , method=RequestMethod.POST)
	public ResponseEntity<?> saveblogcomment(@RequestBody BlogComment blogcomment,HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username=(String) session.getAttribute("username");
		User user=userdao.getUserbyUsername(username);
		try{
			Date date=new Date();
			blogcomment.setCommentedOn(date);
			blogcomment.setCommentedby(user);
			blogpostdao.saveblogcomment(blogcomment);
			return new ResponseEntity<BlogComment>(blogcomment,HttpStatus.OK);
		}
		catch(Exception e){
			Error error=new Error(7,"Unable to enter blog details");
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/getAllBlogcomment/{blogpostid}" , method=RequestMethod.GET)
	public ResponseEntity<?> getallcomments(@PathVariable int blogpostid,HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<BlogComment> blogcomments=blogpostdao.getallcomments(blogpostid);
		return new ResponseEntity<List<BlogComment>>(blogcomments,HttpStatus.OK);
	}

}
