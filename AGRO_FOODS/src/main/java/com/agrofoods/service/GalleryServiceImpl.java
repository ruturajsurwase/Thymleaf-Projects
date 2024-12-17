package com.agrofoods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrofoods.model.Gallery;
import com.agrofoods.repository.GalleryRepository;

@Service
public class GalleryServiceImpl  implements GalleryService{

	
	@Autowired
	GalleryRepository galleryRepository;
	
	
	@Override
	public Gallery saveGalleryImage(Gallery gallery)
	{
	
		Gallery save = galleryRepository.save(gallery);
		return save;
	}


	@Override
	public List<Gallery> getAllImages() {
		return galleryRepository.findAll();
		
	}


	@Override
	public Gallery getImageById(Long id) {
		
		 return galleryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Image not found with id " + id));
	}

}
