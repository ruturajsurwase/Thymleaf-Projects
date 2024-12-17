package com.construction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.construction.model.Gallery;


@Repository
public interface GalleryRepository  extends JpaRepository<Gallery,Long>{

}
