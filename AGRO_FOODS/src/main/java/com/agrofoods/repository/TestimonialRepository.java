package com.agrofoods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agrofoods.model.Testimonial;

@Repository
public interface TestimonialRepository  extends JpaRepository<Testimonial, Long>{

}
