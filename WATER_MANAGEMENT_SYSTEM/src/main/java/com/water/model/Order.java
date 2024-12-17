package com.water.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer_order")
public class Order
{
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long jarQuantity; 

	    private LocalDate orderDate;

	    @ManyToOne
	    @JoinColumn(name="customer_id")
	    private Customer customer; 

	    @ManyToOne
	    @JoinColumn(name="driver_id")
	    private Driver driver; 

	 // Snapshot fields for vehicle details at the time of order
	    private String vehicleNumber;
	    private String vehicleType;

	    public void setVehicleDetails(Vehicle vehicle) {
	        this.vehicleNumber = vehicle.getVehicleNumber();
	        this.vehicleType = vehicle.getVehicleType();
	    }
	    
	    @Transient
	    private String formattedOrderDate; 

}
