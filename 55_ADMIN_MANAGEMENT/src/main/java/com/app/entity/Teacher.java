package com.app.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private String department;
	private String role;
	private String password;
	private double salaryPerMonth;

	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Attendance> attendances;

	
	
	// New fields for salary slip generation
	private double basicPay; // Basic Pay
	private double hra; // House Rent Allowance
	private double conveyanceAllowance; // Conveyance Allowance
	private double educationAllowance; // Education Allowance
	private double washingAllowance; // Washing Allowance
	private double medicalAllowance; // Medical Allowance
	private double dearnessAllowance; // Dearness Allowance
	private double otherAllowances; // Other allowances if any
	private double deductions; // Total deductions (e.g., professional tax)


}
