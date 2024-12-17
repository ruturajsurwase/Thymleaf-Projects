package com.billapp.repository;

import java.util.List;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.billapp.entity.Invoice;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{


    @Query("SELECT i FROM Invoice i LEFT JOIN i.purchasedProducts p WHERE " +
            "(:startDate IS NULL OR i.date >= :startDate) AND " +
            "(:endDate IS NULL OR i.date <= :endDate) AND " +
            "(:clientName IS NULL OR i.clientName LIKE %:clientName%) AND " +
            "(:productName IS NULL OR p.name LIKE %:productName%)")
     List<Invoice> findInvoicesByFilters(
         @Param("startDate") LocalDate startDate,
         @Param("endDate") LocalDate endDate,
         @Param("clientName") String clientName,
         @Param("productName") String productName
     );
    
    
    List<Invoice> findByClientName(String clientName);
  
}
