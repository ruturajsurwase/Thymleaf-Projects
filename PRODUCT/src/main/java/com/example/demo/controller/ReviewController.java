package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;



@Controller
public class ReviewController 
{

    @Autowired
    private ReviewService reviewService;
    
    @GetMapping("/addReview")
    public String showReviewForm(Model model) 
    {
        model.addAttribute("review", new Review()); 
        return "add_review";                          
    }

    @PostMapping("/saveReview")
    public String submitReview(@ModelAttribute("review") Review review, RedirectAttributes redirectAttributes) 
    {
        try {
        	    Review saveReview = reviewService.saveReview(review);
        	    if(saveReview != null)
        	    {
        	    	 redirectAttributes.addFlashAttribute("msg","Review added successfully!");
                     return "redirect:/addReview";               
        	    }
        	    else
        	    {
        	    	 redirectAttributes.addFlashAttribute("msg", "Failed to add review.");
        	    	  return "redirect:/addReview"; 
        	    }
         } 
        catch (Exception e)
        {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "Failed to add review.");
            return "redirect:/addReview";  
        }
    }

    @GetMapping("/reviewlist")
    public String getAllReviews(Model model) 
    {
        List<Review> allReviews = reviewService.getAllReviews(); 
        model.addAttribute("allReviews", allReviews);
        return "index";                                   
    }
    
    @GetMapping("/admin/reviewlist")
    public String getReviwListForAdmin( Model model)
    {
    	  List<Review> allReviews = reviewService.getAllReviews(); 
          model.addAttribute("allReviews", allReviews);
		return "review_list";
    }
    
    @GetMapping("/deletereview/{id}")
	public String deleteProduct(@PathVariable(value = "id") long id) 
    {

		 reviewService.deleteReview(id);
	     return "redirect:/admin/reviewlist";
	}
	
}
