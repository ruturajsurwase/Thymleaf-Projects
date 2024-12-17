package com.agrofoods.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.agrofoods.model.Testimonial;
import com.agrofoods.service.TestimonialService;

@Controller
public class TestimonialController {


	@Autowired
	TestimonialService testimonialService;
	
	
	
	 @GetMapping("/testimonials/new")
	    public String showTestimonialForm(Model model)
	 {
	        model.addAttribute("testimonial", new Testimonial());
	        return "testimonial-form";   // The name of the HTML file (e.g., testimonial-form.html)
	    }
	
	 
	 
	 @PostMapping("/testimonials/save")
	    public String saveTestimonial(@RequestParam("description") String description,
	                                   @RequestParam("imageData") MultipartFile file,
	                                   Model model) {
	        try {
	            Testimonial testimonial = new Testimonial();
	            testimonial.setDescription(description);
	            testimonial.setImageData(testimonialService.convertImageToBytes(file));

	            testimonialService.saveTestimonial(testimonial);

	            model.addAttribute("message", "Testimonial saved successfully!");
	        } catch (IOException e) {
	            model.addAttribute("error", "Error saving testimonial: " + e.getMessage());
	        }

	        return "testimonial-form"; // Redirect or show success message
	    }
	 
 
	 
		
}
