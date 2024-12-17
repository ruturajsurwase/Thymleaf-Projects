package com.water.service;

import java.util.List;

import com.water.model.Customer;
import com.water.model.Driver;

public interface CustomerService {
	
	public void saveCustomer(Customer customer);

	public List<Customer> getAllCustomers();

	public void deleteCustomerById(long id);

	public Customer getCustomerById(Long customerId);

	

	public void updateCustomer(Customer customer);

}
