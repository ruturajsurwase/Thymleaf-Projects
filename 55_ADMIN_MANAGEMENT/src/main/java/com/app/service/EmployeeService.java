package com.app.service;

import java.util.List;

import com.app.entity.Employee;

public interface EmployeeService {

	public List<Employee> getAllEmployees();

	public void saveEmployee(Employee employee);

	public Employee getEmployeeById(Long id);

	public void deleteEmployeeById(Long id);
}
