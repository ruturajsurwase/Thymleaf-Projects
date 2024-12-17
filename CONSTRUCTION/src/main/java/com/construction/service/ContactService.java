package com.construction.service;

import java.util.List;

import com.construction.model.Contact;

public interface ContactService {
    public Contact saveContact(Contact contact);
    public List<Contact> getAllContacts();
    
}
