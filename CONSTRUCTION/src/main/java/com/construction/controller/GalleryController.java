package com.construction.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.construction.model.Gallery;
import com.construction.service.GalleryService;


@Controller
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

   

	@GetMapping("/addphoto")
	public String addphoto()
	{
		return "add_photos";
		
	}
	
	@PostMapping("/gallery/saveImage")
	public String saveImage(@RequestParam("image") MultipartFile file, Model model) {
	    try {
	        Gallery gallery = new Gallery();
	        gallery.setImageData(file.getBytes());
	        gallery.setImageType(file.getContentType()); // Save image as byte[]
	        galleryService.saveGalleryImage(gallery);   // Call service to save
	        
	        // Set success message
	        model.addAttribute("msg", "Image successfully added!");
	    } catch (IOException e) {
	        // Set error message
	        model.addAttribute("msg", "Failed to upload image. Please try again.");
	    }
	    return "add_photos"; // Return to the same page
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
        return "gallery";  // Ensure the name matches the Thymeleaf template file
    }

    @GetMapping("/photolist")
    public String viewPhotoList(Model model) 
    {
        List<Gallery> galleryList = galleryService.getAllImages();
        model.addAttribute("galleryList", galleryList);
        return "photolist";  
    }
    
    
    @PostMapping("/image/delete/{id}")
    public String deleteImage(@PathVariable Long id, Model model) {
        galleryService.deleteImageById(id);
        model.addAttribute("msg", "Image successfully deleted!");
        return "redirect:/photolist";
    }

    
}