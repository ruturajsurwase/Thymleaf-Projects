package com.agrofoods.service;

import com.agrofoods.model.User;
import com.agrofoods.model.UserDto;

public interface UserService {

	
	
	  User save(UserDto userDto);
	  
	  public User getByUsername(String username);
}
