package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;

@Controller
public class ContactController
{
	@Autowired
    private ContactService contactService;

    @PostMapping("/submit_contact")
    public String submitContact(@RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam("phone") String phone,
                                       @RequestParam("message") String message) 
    {
        Contact contact = new Contact(name, email, phone, message);
        contactService.saveContact(contact);
        
        ModelAndView modelAndView = new ModelAndView("contactSuccess");
        modelAndView.addObject("message", "Thank you for contacting us!");
    	return "redirect:/home";
    }
    
    
    
    @GetMapping("/getAllEnquries")
    public String getAllEnquriesDetails(Model model)
    {
    	List<Contact> enquries = contactService.getAllContacts();
    	  model.addAttribute("enquries", enquries);
  	    return "enquries"; 
    	
    }
    
    
    @GetMapping("/deleteEnquiry/{id}")
	public String deleteEnquiry(@PathVariable(value = "id") long id) {

    	contactService.deleteProductById(id);
	     return "redirect:/getAllEnquries";
	}
	
    
    
}
