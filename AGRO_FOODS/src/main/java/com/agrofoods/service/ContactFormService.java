package com.agrofoods.service;

import java.util.List;

import com.agrofoods.model.ContactForm;

public interface ContactFormService {

	
    public void saveFormDetails(ContactForm contactForm);
    
    public List<ContactForm> getAllContactUsDetails();

	public void deleteProductById(long id);
}
