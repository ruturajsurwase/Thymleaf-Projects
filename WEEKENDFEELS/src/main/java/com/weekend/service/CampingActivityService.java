package com.weekend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.weekend.entity.CampingActivity;
import com.weekend.repository.CampingActivityRepository;


@Service
public class CampingActivityService {

	
	 @Autowired
	    private CampingActivityRepository repository;

	    public List<CampingActivity> getAllActivities() {
	        return repository.findAll();
	    }

	    public void saveActivity(CampingActivity activity) {
	        repository.save(activity);
	    }

		public CampingActivity getActivityById(Long id) {
			 return repository.findById(id)
		                .orElseThrow(() -> new RuntimeException("Activity not found"));
		}

		

		public void updateActivity(CampingActivity activity) {
			repository.save(activity); // Save updated activity
			
		}
		
		public void deleteActivityById(Long id) {
			repository.deleteById(id); // Delete activity by ID
			
		}
}
