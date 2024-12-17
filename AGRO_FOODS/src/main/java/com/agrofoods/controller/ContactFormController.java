package com.agrofoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.agrofoods.model.ContactForm;
import com.agrofoods.service.ContactFormService;



@Controller
public class ContactFormController {

	
	
	  @Autowired
	    private ContactFormService contactFormService;
	  
	 
	  
	  @PostMapping("/submit")
	  public String submitContactForm(@ModelAttribute ContactForm contactForm) 
	  {
	        contactFormService.saveFormDetails(contactForm);
	        return "index";              
	    }
	  
	  
	  @GetMapping("/getallContactUs")
	  public String getAllContactUsDetails(Model model)
	  {
		  List<ContactForm> allContactUsDetails = contactFormService.getAllContactUsDetails();
		  model.addAttribute("allContactUsDetails",allContactUsDetails);
		  return "contactus_list";
	  }
	  
	  @GetMapping("/deleteEnquiry/{id}")
		public String deleteEnquiry(@PathVariable(value = "id") long id) {

		  contactFormService.deleteProductById(id);
		     return "redirect:/getallContactUs";
		}
		
	  
	  
	  
	    
}
