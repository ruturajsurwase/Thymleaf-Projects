	package com.water.model;
	
	import java.util.List;
	import jakarta.persistence.CascadeType;
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.OneToMany;
	import lombok.AllArgsConstructor;
	import lombok.Data;
	import lombok.NoArgsConstructor;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Entity
	public class Customer {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	
	    private String name;
	    private String email;
	    private String contactNumber;
	    private String address;
	
	    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	    private List<Order> orders;  
	}
