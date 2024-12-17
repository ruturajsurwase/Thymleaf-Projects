package com.water.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.water.model.Customer;
import com.water.model.Driver;
import com.water.model.Order;
import com.water.model.Vehicle;
import com.water.service.CustomerService;
import com.water.service.DriverService;
import com.water.service.OrderService;
import com.water.service.VehicleService;
import com.water.serviceimpl.EmailService;

@Controller
public class OrderController {

	@Autowired
	DriverService driverService;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;

	@GetMapping("/addOrder")
	public String addOrderPage(Model model) {
		Order order = new Order();

		List<Customer> allCustomers = customerService.getAllCustomers();
		List<Driver> allDrivers = driverService.getAllDrivers();

		model.addAttribute("order", order);
		model.addAttribute("allCustomers", allCustomers);
		model.addAttribute("allDrivers", allDrivers);

		return "add_order";
	}

	@PostMapping("/saveOrder")
	public String saveOrder(@ModelAttribute("order") Order order, RedirectAttributes redirectAttributes) {
		// Get the driver to retrieve the vehicle details
		Driver driver = driverService.getDriverById(order.getDriver().getId());

		if (driver != null && driver.getVehicle() != null) {
			Vehicle vehicle = driver.getVehicle();

			// Set vehicle details in order to snapshot the data
			order.setVehicleNumber(vehicle.getVehicleNumber());
			order.setVehicleType(vehicle.getVehicleType());
		}

		// Save order with the snapshot vehicle details
		orderService.saveOrder(order);
		redirectAttributes.addFlashAttribute("successMessage", "Order added successfully!");
		return "redirect:/orderList";
	}

	@GetMapping("/orderList")
	public String viewOrderList(@RequestParam(value = "customerId", required = false) Long customerId,
			@RequestParam(value = "month", required = false) String month, Model model) {
		List<Order> filteredOrders = orderService.getFilteredOrders(customerId, month);
		List<Customer> allCustomers = customerService.getAllCustomers();
		List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December");

		model.addAttribute("orders", filteredOrders);
		model.addAttribute("allCustomers", allCustomers);
		model.addAttribute("months", months);

		return "order_list";
	}

	@GetMapping("/deleteOrder/{id}")
	public String deleteOrder(@PathVariable(value = "id") long id) {
		orderService.deleteOrderById(id);
		return "redirect:/orderList";
	}

	@GetMapping("/generate_bill")
	public String selectMonthYear(Model model) {
		List<Customer> allCustomers = customerService.getAllCustomers();
		List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August",
				"September", "October", "November", "December");
		List<Integer> years = Arrays.asList(2022, 2023, 2024, 2025); // Add more years as necessary

		model.addAttribute("allCustomers", allCustomers);
		model.addAttribute("months", months);
		model.addAttribute("years", years);

		return "select_month_year";
	}

	@GetMapping("/customer/monthly-bill")
	public String generateMonthlyBill(@RequestParam("customerId") Long customerId, @RequestParam("month") int month,
			@RequestParam("year") int year, Model model) {

		List<Order> orders = orderService.getMonthlyOrdersForCustomer(customerId, month, year);
		Long totalJars = orderService.calculateTotalJarsForMonth(orders);

		double pricePerJar = 30.0; // Set your price per jar
		double totalPayment = totalJars * pricePerJar;

		model.addAttribute("orders", orders);
		model.addAttribute("totalJars", totalJars);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("pricePerJar", pricePerJar);
		model.addAttribute("totalPayment", totalPayment);
		return "monthly-bill";
	}

	@GetMapping("/report")
	public String showReportCriteriaForm(Model model) {
		List<Customer> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		return "report-criteria";
	}

	@PostMapping("/generateMonthlySalesReport")
	public String generateMonthlySalesReport(@RequestParam("month") int month, @RequestParam("year") int year,
			Model model) {
		// Fetch orders for the selected month and year
		List<Order> orders = orderService.findOrdersByMonthAndYear(month, year);

		// Calculate total jars and total payment
		long totalJars = orders.stream().mapToLong(Order::getJarQuantity).sum();
		double pricePerJar = 30.0; // Example price per jar
		double totalPayment = totalJars * pricePerJar;

		// Add data to the model
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("orders", orders);
		model.addAttribute("totalJars", totalJars);
		model.addAttribute("pricePerJar", pricePerJar);
		model.addAttribute("totalPayment", totalPayment);

		return "monthly-sales-report"; // This will render the report page
	}

	@PostMapping("/send-bill")
	public String sendBill(@RequestParam("customerId") Long customerId, @RequestParam("month") int month,
			@RequestParam("year") int year, Model model, RedirectAttributes redirectAttributes) {

		// Retrieve orders and calculate the total for the customer and month
		List<Order> orders = orderService.getMonthlyOrdersForCustomer(customerId, month, year);
		Long totalJars = orderService.calculateTotalJarsForMonth(orders);
		double pricePerJar = 30.0;
		double totalPayment = totalJars * pricePerJar;

		// Get the customer information
		Customer customer = customerService.getCustomerById(customerId);

		// Compose the HTML email body
		StringBuilder emailBody = new StringBuilder();
		emailBody.append("<h2>Monthly Bill for ").append(customer.getName()).append("</h2>");
		emailBody.append("<p><strong>Month:</strong> ").append(month).append("<br>");
		emailBody.append("<strong>Year:</strong> ").append(year).append("</p>");

		// Table header
		emailBody.append("<table border='1' cellpadding='10' cellspacing='0'>");
		emailBody.append("<tr><th>Order Date</th><th>Jar Quantity</th></tr>");

		// Table rows for each order
		for (Order order : orders) {
			emailBody.append("<tr>").append("<td>").append(order.getOrderDate()).append("</td>").append("<td>")
					.append(order.getJarQuantity()).append("</td>").append("</tr>");
		}

		// End table
		emailBody.append("</table>");

		// Add the total details
		emailBody.append("<p><strong>Total Jars Distributed:</strong> ").append(totalJars).append("<br>");
		emailBody.append("<strong>Price per Jar:</strong> ").append(pricePerJar).append("<br>");
		emailBody.append("<strong>Total Payment:</strong> ").append(totalPayment).append("</p>");

		// Closing message
		emailBody.append("<p>Thank you for using our service.<br>Water Management System</p>");

		// Send the email in HTML format
		emailService.sendBillEmail(customer.getEmail(), "Monthly Water Jar Bill", emailBody.toString(), true); // Pass
																												// 'true'
																												// to
																												// indicate
																												// HTML
																												// content

		// Add success message with the customer's name
		redirectAttributes.addFlashAttribute("successMessage",
				"Email sent to " + customer.getName() + " successfully!");

		// Redirect to the bill page after sending the email
		return "redirect:/customer/monthly-bill?customerId=" + customerId + "&month=" + month + "&year=" + year;

	}

}
