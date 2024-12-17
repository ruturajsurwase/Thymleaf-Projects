package com.app.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Employee;
import com.app.repository.EmployeeRepository;
import com.app.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	
	 @Autowired
	 private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAllEmployees() 
	{
		
		return employeeRepository.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		 employeeRepository.save(employee);

	}

	@Override
	public Employee getEmployeeById(Long id)
	{
		 Optional<Employee> optional = employeeRepository.findById(id);
	        Employee employee = null;
	        if (optional.isPresent()) 
	        {
	            employee = optional.get();
	        }
	        else
	        {
	            throw new RuntimeException("Employee not found for id :: " + id);
	        }
	        return employee;
	}

	@Override
	public void deleteEmployeeById(Long id) 
	{
		 employeeRepository.deleteById(id);

	}

}
