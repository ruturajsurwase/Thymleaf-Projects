package com.weekend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weekend.entity.Contact;
import com.weekend.repository.ContactRepository;

@Service
public class ContactService {

	
	  @Autowired
	    private ContactRepository contactRepository;
	  
	  
	  
	  public void saveContact(Contact contact) {
	        contactRepository.save(contact);
	    }
}
