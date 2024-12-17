package com.construction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.construction.model.Partner;


@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
}