package com.construction.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.construction.model.Service;
import com.construction.model.ServiceForm;
import com.construction.repository.ServiceRepository;
import com.construction.service.ServiceLoad;

@Controller
public class ServiceController {

	@Autowired
	private ServiceLoad serviceService;

	

	@GetMapping("/services")
	public String showServicesPage(Model model) {

		model.addAttribute("services", serviceService.getAllServices());

		return "Services";

	}

	@GetMapping("/servicesList")
	public String servicesList(Model model) {

		model.addAttribute("services", serviceService.getAllServices());

		return "servicesList";

	}

	@GetMapping("/addService")
	public String showAddServiceForm(Model model)
	{
		model.addAttribute("serviceForm", new ServiceForm());
		return "add-service";
	}

	@PostMapping("/addService")
	public String addService(@ModelAttribute("serviceForm") ServiceForm serviceForm, 
	                          RedirectAttributes redirectAttributes) throws IOException {
	    try {
	        // Create a new Service entity and copy fields from the form DTO
	        Service service = new Service();
	        service.setTitle(serviceForm.getTitle());
	        service.setDescription(serviceForm.getDescription());

	        // Convert image to byte array and set in Service entity
	        if (serviceForm.getImage() != null && !serviceForm.getImage().isEmpty()) {
	            service.setImage(serviceForm.getImage().getBytes());
	        }

	        // Save the Service entity to the database
	        serviceService.saveService(service);

	        // Add success message as a flash attribute
	        redirectAttributes.addFlashAttribute("msg", "Service added successfully!");

	    } catch (IOException e) {
	        redirectAttributes.addFlashAttribute("msg", "Failed to add service.");
	    }
	    return "redirect:/servicesList";
	}
	
	

@PostMapping("/deleteService/{id}")
public String deleteService(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
        serviceService.deleteServiceById(id);
        redirectAttributes.addFlashAttribute("msg", "Service deleted successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("msg", "Failed to delete service.");
    }
    return "redirect:/servicesList";
}
	
	@GetMapping("/updateServiceForm/{id}")
	public String showUpdateForm(@PathVariable Long id, Model model) {
	    Service service =  serviceService.findById(id);
	    model.addAttribute("serviceForm", service);
	    return "updateServiceForm"; 
	}

	
	@PostMapping("/updateService")
	public String updateService(@ModelAttribute("serviceForm") ServiceForm serviceForm, 
	                            @RequestParam("image") MultipartFile image) {
	    try {
	        // Find existing service
	        Service existingService = serviceService.findById(serviceForm.getId());

	        // Update fields
	        existingService.setTitle(serviceForm.getTitle());
	        existingService.setDescription(serviceForm.getDescription());

	        // If a new image is uploaded, update it
	        if (image != null && !image.isEmpty()) {
	            byte[] imageBytes = image.getBytes();
	            existingService.setImage(imageBytes);
	        }

	        // Save the updated service
	        serviceService.saveService(existingService);

	    } catch (IOException e) {
	        e.printStackTrace();
	        return "redirect:/error"; // Redirect to an error page if needed
	    }

	    return "redirect:/servicesList"; // Redirect to the services list after updating
	}


}