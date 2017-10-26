package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;
import com.niit.model.User;

public interface Frienddao {

	List<User> suggestedusers(String username);
	void friendRequest(Friend friend);//insert into friend

	List<Friend> pendingRequests(String username);

	void updatePendingRequest(Friend friend);
	List<Friend> listOfFriends(String username);
	
	public List<String> getMutualFriends(String username, String otherUsername);

}
