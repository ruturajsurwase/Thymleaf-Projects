package com.water.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.water.model.Vehicle;
import com.water.repository.VehicleRepository;
import com.water.service.VehicleService;

@Service
public class VehicleServiceImpl  implements VehicleService{

	
	
@Autowired
VehicleRepository vehicleRepository;


@Override
	public void saveVehicle(Vehicle vehicle) {
		
	
				vehicleRepository.save(vehicle);
	}
	@Override
	public List<Vehicle> getAllVehicles() {
		// TODO Auto-generated method stub
		return vehicleRepository.findAll();
	}
	@Override
	public void deleteVehicleById(long id) {
		vehicleRepository.deleteById(id);
	}

}
