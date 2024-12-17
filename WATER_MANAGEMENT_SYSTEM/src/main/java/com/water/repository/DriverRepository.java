package com.water.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.water.model.Driver;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
	
	public Driver  findByEmail(String email);
}