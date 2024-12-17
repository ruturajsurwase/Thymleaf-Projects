package com.agrofoods.controller;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.agrofoods.model.Gallery;
import com.agrofoods.service.GalleryService;




@Controller
public class GalleryController {

	@Autowired
	GalleryService galleryService;
	
	
	
	@GetMapping("/addphoto")
	public String addphoto()
	{
		return "add_photos";
		
	}
	
	
	@PostMapping("/gallery/saveImage")
	public String saveImage(@RequestParam("image") MultipartFile file, Model model) 
	{
	    try {
	        Gallery gallery = new Gallery();
	        gallery.setImageData(file.getBytes());                                             // Save image as byte[]
	        Gallery saveGalleryImage = galleryService.saveGalleryImage(gallery);               // Call service to save
	        model.addAttribute("msg", "Image successfully added!");
	    } 
	    catch (IOException e)
	    {
	        model.addAttribute("msg", "Failed to upload image");
	    }
	    return "redirect:/add_photos"; 
	}

	@GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id)
	{
   
        Gallery gallery = galleryService.getImageById(id);
        
    
        if (gallery == null || gallery.getImageData() == null)
        {
            return ResponseEntity.notFound().build();
        }
        
     
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg","image/png") // Modify content type according to your image format
                .body(gallery.getImageData());
    }
    
    @GetMapping("/gallery")
    public String viewGallery(Model model) 
    {
        List<Gallery> galleryList = galleryService.getAllImages();
        model.addAttribute("galleryList", galleryList);
        return "gallary"; 
    }
	
    
    
}
