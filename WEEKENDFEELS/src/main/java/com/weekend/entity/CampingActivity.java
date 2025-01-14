package com.weekend.entity;

import java.math.BigDecimal;

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
public class CampingActivity {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private String imageUrl;
	    private String earlyBirdOffer;
	    private String newYearSpecial;
	    private BigDecimal originalPrice;
	    private BigDecimal offerPrice;
	    private BigDecimal lockPrice;
	    private String checkInTime;
	    private String checkOutTime;
}
