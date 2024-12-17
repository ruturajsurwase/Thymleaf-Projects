package com.weekend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weekend.entity.CampingActivity;




@Repository
public interface CampingActivityRepository extends JpaRepository<CampingActivity, Long>{

}
