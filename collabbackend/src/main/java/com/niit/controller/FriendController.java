package com.niit.controller;

import java.util.ArrayList;
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

import com.niit.dao.Frienddao;
import com.niit.dao.Userdao;
import com.niit.model.Error;
import com.niit.model.Friend;
import com.niit.model.User;

@Controller
public class FriendController {
	
	@Autowired
	private Userdao userdao;
	
	@Autowired
	private Frienddao frienddao;
	
	@RequestMapping(value="/suggestedusers" , method=RequestMethod.GET)
	public ResponseEntity<?> suggestedusers(HttpSession session){
		if( session.getAttribute("username")==null){
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username=(String) session.getAttribute("username");
		List<User> suggestedusers=frienddao.suggestedusers(username);
		return new ResponseEntity<List<User>>(suggestedusers,HttpStatus.OK);
	}
	
	@RequestMapping(value="/friendrequest/{toId}",method=RequestMethod.POST)
	public ResponseEntity<?> friendRequest(@PathVariable String toId,HttpSession session){
    	String username=(String)session.getAttribute("username");
    	if(username==null){
    		Error error=new Error(5,"UnAuthorized Access..");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	Friend friend=new Friend();
    	friend.setFromid(username);
    	friend.setToid(toId);
    	friend.setStatus('P');
    	frienddao.friendRequest(friend);
    	return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
    @RequestMapping(value="/pendingrequests",method=RequestMethod.GET)
    public ResponseEntity<?> pendingRequests(HttpSession session){
    	String username=(String)session.getAttribute("username");
    	if(username==null){
    		Error error=new Error(5,"UnAuthorized Access..");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	List<Friend> pendingRequests=frienddao.pendingRequests(username);
    	return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);
    	
    }
    @RequestMapping(value="/updatependingrequest",method=RequestMethod.PUT)
    public ResponseEntity<?> updatePendingRequest(@RequestBody Friend friend,HttpSession session){
    	String username=(String)session.getAttribute("username");
    	if(username==null){
    		Error error=new Error(5,"UnAuthorized Access..");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	frienddao.updatePendingRequest(friend);//updated status (A/D)
    	return new ResponseEntity<Friend>(friend,HttpStatus.OK);
    }
    @RequestMapping(value="/getuserdetails/{fromId}",method=RequestMethod.GET)
    public ResponseEntity<?> getUserDetails(@PathVariable String fromId,HttpSession session){
    	String username=(String)session.getAttribute("username");
    	if(username==null){
    		Error error=new Error(5,"UnAuthorized Access..");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	User user= userdao.getUserbyUsername(fromId);
    	return new ResponseEntity<User>(user,HttpStatus.OK);
    }
    @RequestMapping(value="/getfriends",method=RequestMethod.GET)
    public ResponseEntity<?> getFriendsList(HttpSession session){
    	String username=(String)session.getAttribute("username");
    	if(username==null){
    		Error error=new Error(5,"UnAuthorized Access..");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	List<Friend> friends=frienddao.listOfFriends(username);
    	return new ResponseEntity<List<Friend>>(friends,HttpStatus.OK);
    }
    
    @RequestMapping(value="/getmutualfriends",method=RequestMethod.PUT)
    //input is List<User> -> suggestedUsers
    public ResponseEntity<?> getMutualFriends(@RequestBody List<User> suggestedUsers,HttpSession session){
    	String username=(String)session.getAttribute("username");
    	if(username==null){
    		Error error=new Error(5,"UnAuthorized Access..");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	Map<String, List<String>> mutualFriends=new HashMap<String, List<String>>();
    	for(User u :suggestedUsers){
    	mutualFriends.put(u.getUsername(), frienddao.getMutualFriends(username,u.getUsername()));
    	System.out.println(mutualFriends.size());
    	}
    	System.out.println(mutualFriends);
    	return new ResponseEntity<Map<String,List<String>>>(mutualFriends,HttpStatus.OK);
    }
    
    @RequestMapping(value="/getmutualfriendsbyfriends",method=RequestMethod.PUT)
    public ResponseEntity<?> getMutualFriendsbyfriends(@RequestBody List<Friend> friends,HttpSession session){
    	String username=(String)session.getAttribute("username");
    	if(username==null){
    		Error error=new Error(5,"UnAuthorized Access..");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	Map<String, List<String>> mutualFriends=new HashMap<String, List<String>>();
    	for(Friend f :friends){
    	mutualFriends.put(f.getFromid(), frienddao.getMutualFriends(username,f.getFromid()));
    	mutualFriends.put(f.getToid(), frienddao.getMutualFriends(username,f.getToid()));
    	System.out.println(mutualFriends.size());
    	}
    	System.out.println(mutualFriends);
    	return new ResponseEntity<Map<String,List<String>>>(mutualFriends,HttpStatus.OK);
    }
    
   
	@RequestMapping(value="/getallmutualfriends/{friend}",method=RequestMethod.PUT)
    public ResponseEntity<?> getallMutualFriend(@PathVariable String friend,HttpSession session){
    	List<User> users=new ArrayList<User>();
    	String username=(String)session.getAttribute("username");
    	if(username==null){
    		Error error=new Error(5,"UnAuthorized Access..");
    		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    	}
    	List<String> mutualFriends=frienddao.getMutualFriends(username,friend);
    	for(String user:mutualFriends){
    		User user1=userdao.getUserbyUsername(user);
    		users.add(user1);
    		}
    	return new ResponseEntity<List<User>>(users,HttpStatus.OK);
    }

}
