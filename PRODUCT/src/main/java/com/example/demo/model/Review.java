package com.example.demo.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review 
{

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String reviewerName;
	    private String content;
	    private String rating;
	 
	    
		public Review(String reviewerName, String content, String rating) {
			super();
			this.reviewerName = reviewerName;
			this.content = content;
			this.rating = rating;
	
		}
	
	
}
