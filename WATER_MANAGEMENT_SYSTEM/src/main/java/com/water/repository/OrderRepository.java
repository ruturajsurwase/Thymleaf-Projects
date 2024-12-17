package com.water.repository;

import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.water.model.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long>
{

        
        // Query based on customer id
        @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.orderDate BETWEEN :startDate AND :endDate")
        List<Order> findByCustomerIdAndOrderDateBetween(@Param("customerId") Long customerId, 
                                                        @Param("startDate") LocalDate startDate, 
                                                        @Param("endDate") LocalDate endDate);
    
        

        @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND FUNCTION('MONTHNAME', o.orderDate) = :month")
        List<Order> findByCustomerIdAndMonth(@Param("customerId") Long customerId, @Param("month") String month);

        
        @Query("SELECT o FROM Order o WHERE FUNCTION('MONTHNAME', o.orderDate) = :month")
        List<Order> findByMonth(@Param("month") String month);

        List<Order> findByCustomerId(Long customerId);
        
    
        @Query("SELECT o FROM Order o WHERE o.driver.vehicle.id = :vehicleId AND o.orderDate BETWEEN :startDate AND :endDate")
        List<Order> findOrdersByVehicleAndDateRange(@Param("vehicleId") Long vehicleId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

  
        @Query("SELECT o FROM Order o WHERE o.driver.id = :driverId AND MONTH(o.orderDate) = :month AND YEAR(o.orderDate) = :year")
        List<Order> findByDriverAndMonthAndYear(@Param("driverId") Long driverId, @Param("month") Integer month, @Param("year") Integer year);

        // Get all orders for a specific driver
        List<Order> findByDriverId(Long driverId);

       
        
        @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND MONTH(o.orderDate) = :month AND YEAR(o.orderDate) = :year")
        List<Order> findByCustomerAndMonth(@Param("customerId") Long customerId, @Param("month") int month, @Param("year") int year);



        List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);				



}
        
        
        


