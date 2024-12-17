package com.weekend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weekend.entity.AccommodationCamp;

@Repository
public interface AccommodationCampRepository extends JpaRepository<AccommodationCamp, Long>{

}
