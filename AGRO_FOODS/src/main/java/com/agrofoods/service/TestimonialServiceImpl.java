package com.agrofoods.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agrofoods.model.Testimonial;
import com.agrofoods.repository.TestimonialRepository;

@Service
public class TestimonialServiceImpl implements TestimonialService {

	
	@Autowired
	TestimonialRepository testimonialRepository;

	@Override
	public byte[] convertImageToBytes(MultipartFile file) throws IOException {
		   return file.getBytes();
	}


	@Override
	public Testimonial saveTestimonial(Testimonial testimonial) {
		  return testimonialRepository.save(testimonial);
	}


	@Override
	public List<Testimonial> getAllTestimonials() {
		 return testimonialRepository.findAll(); 
	}

	
}
