package com.niit.dao;

import com.niit.model.User;

public interface Userdao {

	void registration(User user);
	boolean isvalidemail(String email);
	boolean isvalidusername(String username);
	User login(User user);
	void updateuser(User user);
	User getUserbyUsername(String username);
}
