package com.agrofoods.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.agrofoods.model.Testimonial;
import com.agrofoods.service.TestimonialService;

@Controller
public class AgroProductController {

	@Autowired
	TestimonialService testimonialService;
	
	
	
	@GetMapping("/home")
	public String loadIndexPage(Model model)
	{
		List<Testimonial> testimonials = testimonialService.getAllTestimonials();
	    for (Testimonial testimonial : testimonials) 
	    {
	        String encodedImage = Base64.getEncoder().encodeToString(testimonial.getImageData());
	        testimonial.setEncodedImage(encodedImage); // Set the encoded image in the testimonial object
	    }
	   // System.out.println(testimonials);
	    model.addAttribute("testimonials", testimonials);
	    return "index";
		
	}
	
	@GetMapping("/about_us")
	public String aboutUsPage()
	{
		return "about_us";
	}
	

	@GetMapping("/product")
	public String productPage()
	{
		return "product";
	}
	
	@GetMapping("/contact_us")
	public String contactUsPage()
	{
		return "contact_us";
	}
	
	
	@GetMapping("/medicinal_benefits")
	public String medicinalBenefitsPage()
	{
		return "medicinal_benefits";
	}
	
	
}
