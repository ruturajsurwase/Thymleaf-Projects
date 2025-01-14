package com.weekend.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.weekend.entity.Article;
import com.weekend.service.AccommodationCampService;
import com.weekend.service.ArticleService;
import com.weekend.service.CampingActivityService;
import com.weekend.service.TrekService;

@Controller
public class HomeController {

	
	
	@Autowired
    private CampingActivityService service;
	
	
	@Autowired
	private  AccommodationCampService accommodationService;
	
	@Autowired
	private TrekService trekService;
	
	@Autowired
	 private  ArticleService articleService;
	 
	
	@GetMapping("/index")
	public String indexPage()
	{
		return "index";
	}
	
	

	@GetMapping("/about")
	public String aboutPage()
	{
		return "about";
	}
	
	@GetMapping("/newyear")
	public String newYearPage(Model model)
	{
		model.addAttribute("activities", service.getAllActivities());
		return "newyears";
	}
	
	@GetMapping("/camp")
	public String campPage(Model model)
	{
		model.addAttribute("accommodations",  accommodationService.getAllAccommodations());
		return "camp";
	}
	
	@GetMapping("/trek")
	public String treakPage(Model model)
	{
		model.addAttribute("accommodations",  trekService.getAllTrek());
		return "treks";
	}
	
	
	 @GetMapping("/blog") 
	 public String blogPage(Model model)
	 { List<Article> articles = articleService.getAllArticles();
     model.addAttribute("articles", articles);
		 return "blog";
		 }
	 
	
	@GetMapping("/career")
	public String careersPage()
	{
		return "career";
	}
	
	@GetMapping("/contact")
	public String contactPage()
	{
		return "contact";
	}
	
}
