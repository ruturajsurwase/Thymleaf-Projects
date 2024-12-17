package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;



@Service
public class ContactServiceImpl implements ContactService{

	
	
	@Autowired
    private ContactRepository contactRepository;

	@Override
	public Contact saveContact(Contact contact) {
		return contactRepository.save(contact);
		
	}

	@Override
	public List<Contact> getAllContacts() 
	{
		   return contactRepository.findAll();
	}

	@Override
	public void deleteProductById(long id) 
	{
		contactRepository.deleteById(id);
	}
}
