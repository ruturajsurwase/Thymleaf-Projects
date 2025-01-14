package com.weekend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.weekend.entity.Trek;
import com.weekend.service.TrekService;


@Controller
public class TrekController {

	@Autowired
	private TrekService trekService;

	@GetMapping("/trek/add")
	public String showAddForm(Model model)
	{
		model.addAttribute("trek", new Trek());
		return "trek-form";
	}

	@PostMapping("/trekcamp/save")
	public String saveTrek(@ModelAttribute("trek") Trek trek) 
	{
		trekService.saveTrek(trek);
		return "redirect:/treks";
	}

	
	@GetMapping("/treks")
	public String listTrek(Model model) {
	    List<Trek> allTrek = trekService.getAllTrek();
	    model.addAttribute("allTrek", allTrek);
	    return "trek-list";
	}
	
    // Method to show the update form
    @GetMapping("/trek/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<Trek> trek = trekService.findById(id);
        if (trek.isPresent()) {
            model.addAttribute("trek", trek.get());
            return "trek-update-form";              // Thymeleaf template for update form
        } else {
            // Handle case where trek is not found
            model.addAttribute("error", "Trek not found");
            return "redirect:/treks";
        }
    }
	
    
 // Method to handle the update form submission
    @PostMapping("/trek/update")
    public String updateTrek(@ModelAttribute("trek") Trek trek) {
        trekService.saveTrek(trek); // Save the updated trek details
        return "redirect:/treks"; // Redirect to the trek list page
    }
    
 // Method to delete a trek
    @GetMapping("/trek/delete/{id}")
    public String deleteTrek(@PathVariable("id") Long id) {
        trekService.deleteById(id); // Delete the trek by ID
        return "redirect:/treks"; // Redirect to the trek list page
    }
}
