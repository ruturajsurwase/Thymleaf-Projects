package com.agrofoods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agrofoods.model.ContactForm;


@Repository
public interface ContactFormRepository extends JpaRepository<ContactForm, Long>{

}
