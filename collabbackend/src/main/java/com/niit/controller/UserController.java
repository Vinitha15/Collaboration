package com.niit.controller;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.ProfilePicturedao;
import com.niit.dao.Userdao;
import com.niit.model.Error;
import com.niit.model.ProfilePicture;
import com.niit.model.User;

@Controller
public class UserController {
	@Autowired
	private Userdao userdao;
	
	@Autowired
	private ProfilePicturedao profilepicturedao;
	
	@RequestMapping(value="/registerform", method=RequestMethod.POST)
	public ResponseEntity<?> getregisterform(@RequestBody User user){
		if(!userdao.isvalidemail(user.getEmail()))
		{
			System.out.println(user.getEmail());
			Error error=new Error(2,"Emailid already exists");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
		}
		if(!userdao.isvalidusername(user.getUsername()))
		{
			System.out.println(user.getUsername());
			Error error=new Error(3,"Username already exists");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
			
		}
		try{
			URL url = new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ3IsESXg6Zp1c7Jg9yyZTrjDh6lpbLrcLFwQFf9aM063fe5_60");

            // Read the image ...
        InputStream inputStream      = url.openStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte [] buffer               = new byte[ 1024 ];

        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
           output.write(buffer, 0, n);
        }
        inputStream.close();

        // Here's the content of the image...
        byte [] data = output.toByteArray();
   
			 
        ProfilePicture profilepicture=new ProfilePicture();
		profilepicture.setUsername(user.getUsername());
		profilepicture.setImage(data);
		profilepicturedao.uploadprofilepicture(profilepicture);
			userdao.registration(user);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		catch(Exception e){
			Error error=new Error(1,"Unable to register"+e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session){
		User validUser=userdao.login(user);
		if(validUser==null){
			Error error=new Error(4,"Invalid Username and password");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
		validUser.setOnline(true);
		userdao.updateuser(validUser);
		session.setAttribute("username", validUser.getUsername());
		return new ResponseEntity<User>(validUser,HttpStatus.OK);
	}
	@RequestMapping(value="/logout", method=RequestMethod.PUT)
	public ResponseEntity<?> logout(HttpSession session){
		String username=(String) session.getAttribute("username");
		if(username==null){
			Error error=new Error(5,"please login");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
		User user=userdao.getUserbyUsername(username);
		user.setOnline(false);
		userdao.updateuser(user);
		session.removeAttribute("username");
		session.invalidate();
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	@RequestMapping(value="/getuser", method=RequestMethod.GET)
	public ResponseEntity<?> getuser(HttpSession session){
		String username=(String) session.getAttribute("username");
		if(username==null){
			Error error=new Error(5,"Unauthorized Access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
		User user=userdao.getUserbyUsername(username);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	@RequestMapping(value="/updateprofile", method=RequestMethod.PUT)
	public ResponseEntity<?> updateuser(@RequestBody User user,HttpSession session){
		String username=(String) session.getAttribute("username");
		if(username==null){
			Error error=new Error(5,"Unauthorized Access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		try{
			userdao.updateuser(user);
		return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		catch(Exception e){
			Error error=new Error(6,"Unable to edit"+e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
