package com.water.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.water.model.Driver;
import com.water.repository.DriverRepository;
import com.water.service.DriverService;


@Service
public class DriverServiceImpl  implements DriverService {

	
	
	
	@Autowired
	DriverRepository driverRepository;
	
	
	@Override
	public Driver getByUsername(String username)
	{
		
		return driverRepository.findByEmail(username);
	}


	@Override
	public void saveDriver(Driver driver) {
		driverRepository.save(driver);
		
	}


	@Override
	public List<Driver> getAllDrivers() 
	{
		return driverRepository.findAll();
	}


	@Override
	public Driver getDriverById(long id) {
		
		return driverRepository.findById(id).get();
	}


	@Override
	public void updateDriver(Driver driver) {
	
		
		// Fetch the existing driver by ID
	    Driver existingDriver = driverRepository.findById(driver.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid driver ID"));

	    // Update the existing driver's fields
	    existingDriver.setFirstName(driver.getFirstName());
	    existingDriver.setLastName(driver.getLastName());
	    existingDriver.setEmail(driver.getEmail());
	    existingDriver.setRole(driver.getRole());
	    existingDriver.setPassword(driver.getPassword());
	    existingDriver.setLicenseNumber(driver.getLicenseNumber());
	    existingDriver.setContactNumber(driver.getContactNumber());
	    
	    // Update the vehicle if it exists
	    if (driver.getVehicle() != null) {
	        existingDriver.setVehicle(driver.getVehicle());
	    }

	    // Save the updated driver
	    driverRepository.save(existingDriver);
		
	}


	@Override
	public void deleteDriverById(long id) 
	{
		driverRepository.deleteById(id);
		
	}


	@Override
	public Object findById(Long driverId) {
		// TODO Auto-generated method stub
		return driverRepository.findById(driverId);
	}

}
