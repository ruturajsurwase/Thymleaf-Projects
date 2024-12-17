package com.construction.service;

import java.util.List;

import com.construction.model.Gallery;


public interface GalleryService {
	
	
	
	  public Gallery	saveGalleryImage( Gallery gallery);

	  public List<Gallery> getAllImages();

	  public Gallery getImageById(Long id);
	
	  void deleteImageById(Long id);
}
