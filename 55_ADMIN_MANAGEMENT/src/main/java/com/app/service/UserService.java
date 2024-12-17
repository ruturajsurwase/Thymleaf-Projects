package com.app.service;

import com.app.dto.UserDto;
import com.app.entity.User;


public interface UserService {

	
	  User save(UserDto userDto);
	  
	  public User getByUsername(String username);
}
