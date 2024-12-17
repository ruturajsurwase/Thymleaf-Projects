package com.water.controller;

import com.water.model.Customer;
import com.water.model.Driver;
import com.water.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	// Show form to add a new customer
	@GetMapping("/addCustomer")
	public String showAddCustomerForm(Model model) {
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "add_customer"; // HTML page to render
	}

	// Save a new customer
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
		customerService.saveCustomer(customer);
		redirectAttributes.addFlashAttribute("successMessage", "Customer added successfully!");
		return "redirect:/customerList"; // Redirect to customer list page
	}

	// List all customers
	@GetMapping("/customerList")
	public String listCustomers(Model model) {
		model.addAttribute("customers", customerService.getAllCustomers());
		return "customer_list"; // HTML page to display all customers
	}

	@GetMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable(value = "id") long id) {
		customerService.deleteCustomerById(id);
		return "redirect:/customerList";
	}

	
	
	 @GetMapping("/showFormForUpdateCustomer/{id}")
	    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) 
	 {
	       Customer customer= customerService.getCustomerById(id);
	       
	        model.addAttribute("customer", customer);
	        return "update_customer";
	    }
	 
	 
	    @PostMapping("/updateCutomer")
	    public String updateDriver(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes)
	    {
	    	customerService.updateCustomer(customer);
	        redirectAttributes.addFlashAttribute("successMessage", "Customer updated successfully!");
	        return "redirect:/customerList";
	    }
	 
	 
}
