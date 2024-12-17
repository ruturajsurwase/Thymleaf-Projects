package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Review;
import com.example.demo.repository.ReviewRepository;


@Service
public class ReviewServiceImpl  implements ReviewService{

	
	@Autowired
    private ReviewRepository reviewRepository;


	@Override
	public List<Review> getAllReviews() 
	{
		 return reviewRepository.findAll();
		
	}



	@Override
	public Review saveReview(Review review) 
	{
		  Review savedReview = reviewRepository.save(review);
		return savedReview;
	}



	@Override
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
		
	}

	
	
	  
}

