package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dto.UserDto;
import com.app.entity.Teacher;
import com.app.entity.User;
import com.app.service.TeacherService;
import com.app.service.UserService;

@Controller
public class UserController
{

   @Autowired
    private UserService userService;
   
   @Autowired
	private TeacherService teacherService;
   
   
    @GetMapping("/register")
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
        Teacher teacher = teacherService.getByUsername(username);

        // Admin Login
        if (user != null && user.getPassword().equals(password)) {
            if (user.getRole().equalsIgnoreCase("admin")) {
                return "admin_dashboard";
            } else {
                model.addAttribute("error", "Unauthorized user role!");
                return "login";
            }
        } 
        // Teacher Login
        else if (teacher != null && teacher.getPassword().equals(password)) {
            model.addAttribute("teacher", teacher);                                  // Pass teacher details to the view
            return "teacher_dashboard";
        } 
        // Invalid credentials
        else {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
    }

    
    
    @GetMapping("/teacherdashboard/{id}")
    public String getTeacherDashboard(@PathVariable("id") Long id, Model model) 
    {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);      // Add teacher details to the model
        return "teacher_dashboard";               // Return the view for the teacher dashboard
    }
    

    
    @PostMapping("/logout")
    public String logout() 
    {
    	return "login";
    }
}
