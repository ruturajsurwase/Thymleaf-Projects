package com.weekend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weekend.entity.Trek;



@Repository
public interface TrekRepository extends JpaRepository<Trek, Long>{

}