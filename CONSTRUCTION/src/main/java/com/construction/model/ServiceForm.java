package com.construction.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;



@Data

public class ServiceForm {
	 private Long id;
	    private String title;
	    private String description;
	    private String base64Image; // For displaying the current image
	    private MultipartFile image; // For uploading a new image
}
