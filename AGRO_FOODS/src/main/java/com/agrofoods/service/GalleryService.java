package com.agrofoods.service;

import java.util.List;

import com.agrofoods.model.Gallery;

public interface GalleryService 
{
   public Gallery	saveGalleryImage( Gallery gallery);

public List<Gallery> getAllImages();

public Gallery getImageById(Long id);
}
