package com.water.controller;

import java.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.water.model.Order;
import com.water.model.Vehicle;
import com.water.service.OrderService;
import com.water.service.VehicleService;

@Controller
public class VehicleController

{
	@Autowired
	VehicleService vehicleService;

	@Autowired
	OrderService orderService;

	@GetMapping("/addVehicle")
	public String addVehiclePage(Model model) {

		Vehicle vehicle = new Vehicle();
		model.addAttribute("vehicle", vehicle); // Add it to the model
		return "add_Vehicle";

	}

	@PostMapping("/savevehicle")
	public String saveVehicle(@ModelAttribute("vehicle") Vehicle vehicle, RedirectAttributes redirectAttributes) {

		vehicleService.saveVehicle(vehicle);
		redirectAttributes.addFlashAttribute("successMessage", "Driver added successfully!");
		return "redirect:/showVehicleList";
	}

	@GetMapping("/showVehicleList")
	public String showDriverList(Model model) {
		model.addAttribute("listVehicle", vehicleService.getAllVehicles());
		return "vehicle_list";
	}

	@GetMapping("deleteVehicle/{id}")
	public String deleteDriver(@PathVariable(value = "id") long id) {
		vehicleService.deleteVehicleById(id);
		return "redirect:/showVehicleList";
	}

	@GetMapping("/vehicle/total-jars-page")
	public String showJarDistributionPage(Model model)
	{
		// Fetch and pass vehicles to the view for dropdown
		model.addAttribute("vehicles", vehicleService.getAllVehicles());
		return "vehicle-jar-distribution"; // Corresponds to vehicle-jar-distribution.html
	}

	@GetMapping("/vehicle/jar-distribution")
	public String getJarDistribution(@RequestParam("vehicleId") Long vehicleId,
	                                 @RequestParam("year") int year,
	                                 @RequestParam("month") int month,
	                                 Model model) {	
		
		LocalDate startDate = LocalDate.of(year, month, 1);
		int lastDayOfMonth = startDate.lengthOfMonth();
		LocalDate endDate = LocalDate.of(year, month, lastDayOfMonth);

		
	    // Fetch all orders for the vehicle within the selected month
	    List<Order> orders = orderService.findOrdersByVehicleAndDateRange(vehicleId, startDate, endDate);

	    if (orders.isEmpty()) {
	        System.out.println("No orders found for vehicle " + vehicleId + " in the specified month.");
	    } else {
	        System.out.println("Orders retrieved: " + orders.size());
	    }

	    // Group the orders by date and calculate the total jars per day
	    Map<LocalDate, Long> dateWiseDistribution = orders.stream()
	            .collect(Collectors.groupingBy(Order::getOrderDate, Collectors.summingLong(Order::getJarQuantity)));

	    // Calculate the total jar quantity for the month
	    Long totalJars = dateWiseDistribution.values().stream().mapToLong(Long::longValue).sum();

	    // Add data to the model for use in the Thymeleaf view
	    model.addAttribute("dateWiseDistribution", dateWiseDistribution);
	    model.addAttribute("totalJars", totalJars);

	    // You can also pass vehicle information or other data to the view if needed
	    model.addAttribute("vehicleId", vehicleId);
	    model.addAttribute("year", year);
	    model.addAttribute("month", month);

	    // Return the name of the Thymeleaf template (HTML file)
	    return "vehicle-jar-distribution";
	}

}