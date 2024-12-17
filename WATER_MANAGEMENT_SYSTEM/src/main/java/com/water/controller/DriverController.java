package com.water.controller;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.water.model.Driver;
import com.water.model.Order;
import com.water.repository.OrderRepository;
import com.water.service.DriverService;
import com.water.service.VehicleService;

@Controller
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/adddriver")
    public String addDriverPage(Model model) 
    {
        Driver driver = new Driver();              // Create a new empty Driver object
        model.addAttribute("driver", driver);     // Add it to the model
        return "add_driver";
    }

    @PostMapping("/saveDriver")
    public String saveDriver(@ModelAttribute("driver") Driver driver, RedirectAttributes redirectAttributes) {
        driverService.saveDriver(driver);
        redirectAttributes.addFlashAttribute("successMessage", "Driver added successfully!");
        return "redirect:/showDriverList";
    }

    @GetMapping("/showDriverList")
    public String showDriverList(Model model) {
        model.addAttribute("listDrivers", driverService.getAllDrivers());
        return "driver_list";
    }

    @GetMapping("/showFormForUpdateDriver/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Driver driver = driverService.getDriverById(id);
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("driver", driver);
        return "update_driver";
    }

    @PostMapping("/updateDriver")
    public String updateDriver(@ModelAttribute("driver") Driver driver, RedirectAttributes redirectAttributes)
    {
        driverService.updateDriver(driver);
        redirectAttributes.addFlashAttribute("successMessage", "Driver updated successfully!");
        return "redirect:/showDriverList";
    }

    @GetMapping("/deleteDriver/{id}")
    public String deleteDriver(@PathVariable(value = "id") long id) {
        driverService.deleteDriverById(id);
        return "redirect:/showDriverList";
    }
   
    @GetMapping("/driver/{id}/dashboard")
    public String showDriverDashboard(@PathVariable("id") Long driverId, Model model) {
        Driver driver = driverService.getDriverById(driverId);
        model.addAttribute("driver", driver);
      
        return "driver_dashboard"; // Make sure the template exists
    }
    
    
    @GetMapping("/driver/{id}/jar-distribution")
    public String showDriverOrders(@PathVariable("id") Long driverId, Model model)
    {
        Driver driver = driverService.getDriverById(driverId);
        List<Order> orders = orderRepository.findByDriverId(driverId);

        model.addAttribute("driver", driver);
        model.addAttribute("orders", orders);

        return "driverOrders";
    }
 
    @GetMapping("/driver/{id}/orders/filter")
    public String filterDriverOrders(@PathVariable("id") Long driverId, @RequestParam(value = "month", required = false) Integer month,
                                     @RequestParam(value = "year", required = false) Integer year, Model model)
    {
        Driver driver = driverService.getDriverById(driverId);

        // Ensure the months and years are being passed correctly to the template
        List<Integer> months = IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList());
       // System.out.println(months);
        List<Integer> years = IntStream.rangeClosed(2020, Year.now().getValue()).boxed().collect(Collectors.toList());
        // System.out.println(years);
        
        List<Order> filteredOrders;
        if (month == null || month == 0 || year == null || year == 0) 
        {
            // Show all orders if no month or year is selected
            filteredOrders = orderRepository.findByDriverId(driverId);
        } 
        else 
        {
            // Fetch filtered orders based on month and year
            filteredOrders = orderRepository.findByDriverAndMonthAndYear(driverId, month, year);
        }

        model.addAttribute("driver", driver);
        model.addAttribute("orders", filteredOrders);
        model.addAttribute("months", months);
        model.addAttribute("years", years);

        return "driverOrders";
    }
    
    
 
    
}
