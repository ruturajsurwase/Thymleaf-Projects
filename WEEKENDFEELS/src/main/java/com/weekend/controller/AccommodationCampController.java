package com.weekend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.weekend.entity.AccommodationCamp;
import com.weekend.service.AccommodationCampService;

@Controller
public class AccommodationCampController {

	
	@Autowired
	private  AccommodationCampService accommodationService;
	
	
	  @GetMapping("/accommodations")
	    public String listAccommodations(Model model) 
	  {
	        List<AccommodationCamp> accommodations = accommodationService.getAllAccommodations();
	        model.addAttribute("accommodations", accommodations);
	        return "accommodation-list";
	    }



	  @GetMapping("/accommodations/add")
	    public String showAddForm(Model model) {
	        model.addAttribute("accommodation", new AccommodationCamp());
	        return "accommodationcamp-form";
	    }

	    @PostMapping("/accommodations/save")
	    public String saveAccommodation(@ModelAttribute("accommodation") AccommodationCamp accommodation) {
	        accommodationService.saveAccommodation(accommodation);
	        return "redirect:/accommodations";
	    }

	    @GetMapping("/accommodationcamp/edit/{id}")
	    public String showUpdateForm(@PathVariable Long id, Model model) {
	        AccommodationCamp accommodationCamp = accommodationService.getAccommodationCampById(id);
	        model.addAttribute("accommodationCamp", accommodationCamp);
	        return "update-accommodationcamp-form"; // Corrected view name
	    }


	    // Handle update form submission
	    @PostMapping("/accommodation/update")
	    public String updateActivity(@ModelAttribute AccommodationCamp activity)
	    {
	    	accommodationService.updateAccommodationCamp(activity);
	    	  return "redirect:/accommodations"; // Redirect to activity list
	    }

	    // Handle delete
	    @GetMapping("/accommodationcamp/delete/{id}")
	    public String deleteActivity(@PathVariable Long id)
	    {
	    	accommodationService.deleteAccommodationCampById(id);
	    	return "redirect:/accommodations";// Redirect to activity list
	    }

}
