package com.agrofoods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrofoods.model.ContactForm;
import com.agrofoods.repository.ContactFormRepository;

@Service
public class ContactFormServiceImpl implements ContactFormService {

	@Autowired
	private ContactFormRepository contactFormRepository;

	@Override
	public void saveFormDetails(ContactForm contactForm) {
		contactFormRepository.save(contactForm);

	}

	@Override
	public List<ContactForm> getAllContactUsDetails() {
		
		return contactFormRepository.findAll();
	}

	@Override
	public void deleteProductById(long id) {
		contactFormRepository.deleteById(id);
		
	}
	
	
	

}
