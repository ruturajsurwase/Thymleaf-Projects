package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Review;

public interface ReviewService {

	

	public List<Review> getAllReviews();

	public Review saveReview(Review review);
	
	public void deleteReview(Long id);

}
