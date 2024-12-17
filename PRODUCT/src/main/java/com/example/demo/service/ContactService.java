package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Contact;

public interface ContactService {

	
	  public Contact saveContact(Contact contact);
	  
	  public List<Contact> getAllContacts();

	   public void deleteProductById(long id);
}
