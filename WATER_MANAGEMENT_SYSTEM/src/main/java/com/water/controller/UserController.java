package com.water.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.water.dto.UserDto;
import com.water.model.Driver;
import com.water.model.User;
import com.water.service.DriverService;
import com.water.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private DriverService driverService;

	@GetMapping("/")
	public String viewHomePage() {

		return "admin_dashboard";
	}

	@GetMapping("/register")
	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
		return "register";
	}

	@PostMapping("/registration")
	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
		userService.save(userDto);
		model.addAttribute("message", "Registered Successfully....!!!");
		return "login"; // Redirecting to login page after successful registration
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login"; // Renders the login page
	}

	@PostMapping("/loginsaved")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		User user = userService.getByUsername(username);
		Driver driver = driverService.getByUsername(username);

		// Admin Login
		if (user != null && user.getPassword().equals(password)) {
			if (user.getRole().equalsIgnoreCase("admin")) {
				return "admin_dashboard";
			} else {
				model.addAttribute("error", "Unauthorized user role!");
				return "login";
			}
		}
		// driver Login
		else if (driver != null && driver.getPassword().equals(password)) {
			model.addAttribute("driver", driver);
			return "driver_dashboard";
		}

		else {
			model.addAttribute("error", "Invalid username or password!");
			return "login";
		}
	}

	@PostMapping("/logout")
	public String logout() {
		return "login";
	}

}
