package com.example.demo.model;

import java.math.BigDecimal;





import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import jakarta.persistence.Transient;
import lombok.Data;


@Entity
@Table(name = "product")
@Data
public class Product {

	public Product() {
		// TODO Auto-generated constructor stub
	}


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;
	    private BigDecimal price;
	    private String description;
	    @Lob
	    private byte[] image;
	    
	      @Transient
	    private String base64Image;

	    // Getters and Setters


}
