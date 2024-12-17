package com.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.UserDto;
import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User save(UserDto userDto) 
	{
		 User user=new User();
		 
		 user.setFullname(userDto.getFullname());
		 user.setEmail(userDto.getEmail());
		 user.setPassword(userDto.getPassword());
		 user.setRole(userDto.getRole());
		 
		 User saved = userRepository.save(user);
		 
		return saved;
	}
	
	
	
	public User getByUsername(String username) {
		
		
		User byEmail = userRepository.findByEmail(username);
		
		return byEmail;
	}

}
