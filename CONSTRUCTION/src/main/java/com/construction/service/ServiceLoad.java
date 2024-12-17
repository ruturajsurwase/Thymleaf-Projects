package com.construction.service;

import java.util.List;

import com.construction.model.Service;

public interface ServiceLoad {

	public List<Service> getAllServices();

	public Service saveService(Service service);

	public void deleteServiceById(Long id);

	public Service findById(Long id);
}
