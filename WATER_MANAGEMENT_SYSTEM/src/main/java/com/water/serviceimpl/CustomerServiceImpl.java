package com.water.serviceimpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.water.model.Customer;
import com.water.model.Driver;
import com.water.repository.CustomerRepository;
import com.water.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService  {

	
    @Autowired
    private CustomerRepository customerRepository;
    
	@Override
	public void saveCustomer(Customer customer) {
		 customerRepository.save(customer);
}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
	      return customerRepository.findAll();
	}

	@Override
	public void deleteCustomerById(long id) {
		// TODO Auto-generated method stub
		customerRepository.deleteById(id);
	}

	@Override
	public Customer getCustomerById(Long customerId) 
	{
		 Customer customer = customerRepository.findById(customerId).get();
		return customer;
	}

	@Override
	public void updateCustomer(Customer customer) {
	
		Customer existingCustomer = customerRepository.findById(customer.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid Customer ID"));
	
		existingCustomer.setName(customer.getName());
		
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setContactNumber(customer.getContactNumber());
		existingCustomer.setAddress(customer.getAddress());
		existingCustomer.setOrders(customer.getOrders());
	
	
	
		customerRepository.save(existingCustomer);
	
	
	}


}
