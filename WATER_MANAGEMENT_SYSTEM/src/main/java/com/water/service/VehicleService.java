package com.water.service;


import java.util.List;

import com.water.model.Vehicle;

public interface VehicleService {

	public void saveVehicle(Vehicle vehicle);
public List<Vehicle>	getAllVehicles();
public void deleteVehicleById(long id);

}
