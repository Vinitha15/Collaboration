package com.niit.dao;

import com.niit.model.ProfilePicture;

public interface ProfilePicturedao {

	void uploadprofilepicture(ProfilePicture profilepic);
	ProfilePicture getProfilePicture(String username);
}
