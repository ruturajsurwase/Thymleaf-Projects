package com.construction.service;

import com.construction.model.User;
import com.construction.model.UserDto;

public interface UserService 
{
	  User save(UserDto userDto);
	  
	  public User getByUsername(String username);
}
