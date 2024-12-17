package com.construction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.construction.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>{

}
