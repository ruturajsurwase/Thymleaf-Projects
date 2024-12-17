package com.construction.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.construction.model.Service;
import com.construction.service.PartnerService;
import com.construction.service.ServiceLoad;

@Controller
public class HomeController 
{
    @Autowired
    private PartnerService partnerService;
    
    
    @Autowired
    private ServiceLoad serviceService;
    
	@GetMapping("/index")
	public String indexPage(Model model) 
	{
		model.addAttribute("partners", partnerService.getAllPartners());
		return "index";
	}

	@GetMapping("/aboutUs")
	public String aboutUsPage() {
		return "aboutUs";
	}
	/*
	@GetMapping("/services")
	public String servicesPage(Model model) 
	{
		
		 model.addAttribute("services", serviceService.getAllServices()); return
		 "Services";
		 
	}
 */
	/*
	 * @GetMapping("/gallery") public String galleryPage() { return "gallery"; }
	 * 
	 */
	
	
	@GetMapping("/contactUs")
	public String contactPage() {
		return "contactUs";
	}
	
}
