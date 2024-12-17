package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;



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

	@Override
	public User getByUsername(String username) 
	{
		User byEmail = userRepository.findByEmail(username);
		
		return byEmail;
	}
	
	
	
	
	
	
}
