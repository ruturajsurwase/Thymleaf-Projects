package com.billapp.entity.service;

import com.billapp.entity.User;
import com.billapp.entity.UserDto;

public interface UserService {

	
	
	User save(UserDto userDto);
	  
	  public User getByUsername(String username);
}
