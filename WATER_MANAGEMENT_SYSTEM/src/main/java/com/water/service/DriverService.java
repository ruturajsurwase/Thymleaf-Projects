package com.water.service;

import java.util.List;


import com.water.model.Driver;

public interface DriverService
{
	public Driver getByUsername(String username);

	public void saveDriver(Driver driver);

	public List<Driver> getAllDrivers();

	public Driver getDriverById(long id);

	public void updateDriver(Driver driver);

	public void deleteDriverById(long id);

	public Object findById(Long driverId);

	
}
