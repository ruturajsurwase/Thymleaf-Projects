package com.agrofoods.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.agrofoods.model.Testimonial;

public interface TestimonialService {


	 public Testimonial saveTestimonial(Testimonial testimonial);
	public byte[] convertImageToBytes(MultipartFile file) throws IOException;
	
	 public List<Testimonial> getAllTestimonials();
}
