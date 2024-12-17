package com.construction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.construction.model.Contact;
import com.construction.service.ContactService;

@Controller
@RequestMapping("/contacts")
public class ContactController {

	
	@Autowired
    private ContactService contactService;
    

    @Autowired
    private JavaMailSender mailSender;
    
    @PostMapping("/submit")
    public String submitContactForm(Contact contact, RedirectAttributes redirectAttributes) 
    {
        contactService.saveContact(contact);
        sendInquiryEmailToCompany(contact);
        redirectAttributes.addFlashAttribute("msg", "Thank you for your inquiry. We'll get back to you soon.");
        return "redirect:/index";
    }

    private void sendInquiryEmailToCompany(Contact contact)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("ruturajsurwase@gmail.com");  // Replace with your company email
        message.setSubject("New Inquiry Received");
        message.setText(
                "A new inquiry has been submitted:\n\n" +
                "Name: " + contact.getFirstname() + "\n" +
                "Email: " + contact.getEmail() + "\n" +
                "Contact: " + contact.getContact() + "\n" +
                "Address: " + contact.getAddress()+ "\n" +
                "Message: \n" + contact.getMessage() + "\n\n" +
                "Please follow up with this inquiry promptly."
        );

        mailSender.send(message);
    }
    
    
    // Display all contacts
    @GetMapping("/getallContactUs")
    public String getAllContacts(Model model) 
    {
        model.addAttribute("contacts", contactService.getAllContacts());
        return "contacts-list"; // Create a Thymeleaf template to list contacts
    }
}
