package com.water.service;

import com.water.dto.UserDto;
import com.water.model.User;

public interface UserService {

	
	public   User save(UserDto userDto);
	  
	  public User getByUsername(String username);
}
