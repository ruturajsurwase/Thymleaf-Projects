package com.water.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.water.dto.UserDto;
import com.water.model.User;
import com.water.repository.UserRepository;
import com.water.service.UserService;
@Service
public class UserServiceImpl  implements UserService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User save(UserDto userDto) {
 User user=new User();
		 
		 user.setFullname(userDto.getFullname());
		 user.setEmail(userDto.getEmail());
		 user.setPassword(userDto.getPassword());
		 user.setRole(userDto.getRole());
		 
		 User saved = userRepository.save(user);
		 
		return saved;
	}

	@Override
	public User getByUsername(String username) {

		User byEmail = userRepository.findByEmail(username);
		
		return byEmail;
	}

}
