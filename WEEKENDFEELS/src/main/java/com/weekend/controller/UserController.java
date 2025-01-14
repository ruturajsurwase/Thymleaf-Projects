package com.weekend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weekend.entity.User;
import com.weekend.entity.UserDto;
import com.weekend.service.UserService;




@Controller
public class UserController {

	
	@Autowired
    private UserService userService;

@GetMapping("/")
public String viewHomePage() 
{

    return "admin_dashboard";
}
@GetMapping("/registration")
public String getRegistrationPage(@ModelAttribute("user") UserDto userDto)
{
    return "register";
}


@PostMapping("/registration")
public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) 
{
    userService.save(userDto);
    model.addAttribute("message", "Registered Successfully....!!!");
    return "login";                                                             // Redirecting to login page after successful registration
}


@GetMapping("/login")
public String showLoginPage() 
{
    return "login";                                          // Renders the login page
}

@PostMapping("/loginsaved")
public String login(@RequestParam("username") String username,
                    @RequestParam("password") String password, Model model) 
{
    User user = userService.getByUsername(username);


    // Admin Login
    if (user != null && user.getPassword().equals(password))
    {
        if (user.getRole().equalsIgnoreCase("admin"))
        {
            return "admin_dashboard";
        } 
        else 
        {
            model.addAttribute("error", "Unauthorized user role!");
            return "login";
        }
    }
	return "login"; 
}

@PostMapping("/logout")
public String logout() 
{
	return "login";
}

}
