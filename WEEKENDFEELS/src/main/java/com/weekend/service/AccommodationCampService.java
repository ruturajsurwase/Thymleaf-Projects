package com.weekend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weekend.entity.AccommodationCamp;

import com.weekend.repository.AccommodationCampRepository;


@Service
public class AccommodationCampService {

	
	@Autowired
	  private AccommodationCampRepository accommodationRepository;

	   

	    public List<AccommodationCamp> getAllAccommodations() {
	        return accommodationRepository.findAll();
	    }



		public void saveAccommodation(AccommodationCamp accommodation) {
			 accommodationRepository.save(accommodation);
			
		}



		public AccommodationCamp getAccommodationCampById(Long id) {
			 return accommodationRepository.findById(id)
		                .orElseThrow(() -> new RuntimeException("Activity not found"));
		}



		public void updateAccommodationCamp(AccommodationCamp activity)
		{
			accommodationRepository.save(activity);
			
		}



		public void deleteAccommodationCampById(Long id) {
			accommodationRepository.deleteById(id);
			
		}
}
