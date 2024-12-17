package com.weekend.service;

import com.weekend.entity.User;
import com.weekend.entity.UserDto;

public interface UserService {

	
	
	User save(UserDto userDto);
	  
	  public User getByUsername(String username);
}
