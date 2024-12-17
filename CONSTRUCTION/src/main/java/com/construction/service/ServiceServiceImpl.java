package com.construction.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.construction.model.Service;
import com.construction.repository.ServiceRepository;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceLoad {

	 @Autowired
	    private ServiceRepository serviceRepository;
	
	@Override
	public List<Service> getAllServices() 
	{
		 return serviceRepository.findAll().stream().map(service -> {
	            String base64Image = Base64.getEncoder().encodeToString(service.getImage());
	            service.setBase64Image(base64Image);
	            return service;
	        }).collect(Collectors.toList());
	}

	@Override
	public Service saveService(Service service) {
		  return serviceRepository.save(service);
	}

	@Override
	public void deleteServiceById(Long id) {
		 serviceRepository.deleteById(id);
		
	}

	@Override
	public Service findById(Long id) 
	{   Service service = serviceRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Service not found with id " + id));
    
String base64Image = Base64.getEncoder().encodeToString(service.getImage());
service.setBase64Image(base64Image);

return service;
	}
	

}
