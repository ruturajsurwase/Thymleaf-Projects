package com.weekend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.weekend.entity.Trek;
import com.weekend.repository.TrekRepository;

@Service
public class TrekService {

	@Autowired
	private TrekRepository trekRepository;
	
	
	public void saveTrek(Trek trek) 
	{
		trekRepository.save(trek);
		
	}


	public List<Trek> getAllTrek() 
	{
		return  trekRepository.findAll();
		
	}


	public Optional<Trek> findById(Long id) {
		 return trekRepository.findById(id);
	}


	public void deleteById(Long id) {
		  trekRepository.deleteById(id);
		
	}

	
	
	
}
