package com.agrofoods.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Testimonial {

	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
	   
	    private String description;
	   
	    @Lob
	    @Column(name = "image_data", columnDefinition="LONGBLOB", nullable = false)
	    private byte[] imageData;

	    private String encodedImage;
	
	
}
