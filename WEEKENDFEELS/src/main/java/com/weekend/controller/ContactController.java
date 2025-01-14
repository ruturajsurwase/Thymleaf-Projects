package com.weekend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.weekend.entity.Contact;
import com.weekend.service.ContactService;

@Controller
public class ContactController {
	
	
	
	@Autowired
	    private ContactService contactService;
	
	
	@Autowired
    private JavaMailSender mailSender;

	    @PostMapping("/submitContact")
	    public String  submitContact(@ModelAttribute Contact contact,RedirectAttributes redirectAttributes) 
	    {
	        contactService.saveContact(contact);
	        sendInquiryEmailToCompany(contact);
	        redirectAttributes.addFlashAttribute("successMessage", "Enquiry sent successfully!");
	      
	        return "contact";            // Redirect to a thank-you page
	    }
	    
	    
	    private void sendInquiryEmailToCompany(Contact contact)
	    {
	        SimpleMailMessage message = new SimpleMailMessage();
	        
	        message.setTo("ruturajsurwase@gmail.com");      // Replace with your company email
	        message.setSubject("New Inquiry Received");
	        message.setText(
	                "A new inquiry has been submitted:\n\n" +
	                "Name: " + contact.getName() + "\n" +
	                "Email: " + contact.getEmail() + "\n" +
	                "Contact: " + contact.getPhone() + "\n" +
	                "Address: " + contact.getSubject()+ "\n" +
	                "Message: \n" + contact.getMessage() + "\n\n" +
	                "Please follow up with this inquiry promptly."
	        );

	        mailSender.send(message);
	    }
	    
}
