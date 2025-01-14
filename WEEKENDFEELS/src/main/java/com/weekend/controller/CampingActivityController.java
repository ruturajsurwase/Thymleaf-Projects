package com.weekend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.weekend.entity.CampingActivity;
import com.weekend.service.CampingActivityService;


@Controller
public class CampingActivityController {

	
	
	@Autowired
    private CampingActivityService service;

    @GetMapping("/listActivities")
    public String listActivities(Model model) {
        model.addAttribute("activities", service.getAllActivities());
        return "ActivityList";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("activity", new CampingActivity());
        return "add-activity";
    }

    @PostMapping("/save")
    public String saveActivity(@ModelAttribute CampingActivity activity) {
        service.saveActivity(activity);
        return "redirect:/listActivities";
    }
    
    // Show update form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
    	CampingActivity activity = service.getActivityById(id);
        model.addAttribute("activity", activity);
        return "update-activity-form"; // View name for update form
    }

    // Handle update form submission
    @PostMapping("/activities/update")
    public String updateActivity(@ModelAttribute CampingActivity activity)
    {
    	service.updateActivity(activity);
    	  return "redirect:/listActivities"; // Redirect to activity list
    }

    // Handle delete
    @GetMapping("/delete/{id}")
    public String deleteActivity(@PathVariable Long id)
    {
    	service.deleteActivityById(id);
    	return "redirect:/listActivities";// Redirect to activity list
    }
}
