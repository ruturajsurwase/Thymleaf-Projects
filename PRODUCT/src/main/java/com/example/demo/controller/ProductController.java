package com.example.demo.controller;


import java.io.IOException;

import java.math.BigDecimal;
import java.util.Base64;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.service.ProductService;
import com.example.demo.service.ReviewService;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewService reviewService;

	@Value("${uploadDir}")
	private String uploadDirectory;


	@GetMapping("/about_us")
	public String aboutUsPage() {
		return "about_us";
	}

	@GetMapping("/clients_feedback")
	public String clientFeedbackPage() {
		return "clients_feedback";
	}

	@GetMapping("/contact_us")
	public String contactUsPage() {
		return "contact_us";
	}

	@GetMapping("/addproduct")
	public String addProductPage() {
		return "add_product.html";
	}

	@GetMapping("/ghee")
	public String gheePage() {
		return "ghee";
	}

	@GetMapping("/paneer")
	public String paneerPage() {
		return "paneer";
	}
	
	@GetMapping("/butter")
	public String butterPage() {
		return "butter";
	}
	
	@GetMapping("/soya-paneer")
	public String soyapaneerPage() 
	{
		return "soyapaneer";
	}
	
	@GetMapping("/cheese")
	public String cheesPage() 
	{
		return "cheese";
	}
	
	
	
	
	
	// Method to load the home page with the product list
	@GetMapping("/home")
	public String addProductPage(Model model) 
	{
		List<Product> products = productService.getAllActiveProducts();
		
		  List<Review> allReviews = reviewService.getAllReviews();
		  
		  model.addAttribute("products", products);
		  model.addAttribute("allReviews", allReviews);
		  
		return "index";
	}


	
	@PostMapping("/product/saveProduct")
	public String saveProduct(@RequestParam(value = "id", required = false) Long id, 
	                          @RequestParam("name") String name, 
	                          @RequestParam("price") BigDecimal price, 
	                          @RequestParam("description") String description, 
	                          Model model, 
	                          @RequestParam("image") MultipartFile file) 
	{
	    try {
	        // Create a new product object or fetch an existing one if updating
	        Product product = (id != null) ? productService.getProduct(id) : new Product();
	        product.setName(name);
	        product.setPrice(price);
	        product.setDescription(description);

	        // Handle image upload
	        if (!file.isEmpty()) {
	            String fileName = file.getOriginalFilename();
	            if (fileName == null || fileName.contains("..")) {
	                model.addAttribute("msg", "Invalid file name");
	                return "add_product";
	            }

	            // Save image file to the filesystem or as bytes to the database
	            byte[] imageData = file.getBytes();
	            product.setImage(imageData);
	        }

	        // Save the product (will create new or update based on the presence of id)
	        productService.saveProduct(product);

	        model.addAttribute("msg", "Product saved successfully");
	        return "redirect:/productlist"; // Redirect to product list after saving

	    } catch (Exception e) {
	        model.addAttribute("msg", "Error saving product");
	        return "add_product"; // Return to the add product form in case of error
	    }
	}

	
	@GetMapping("/product/productdetails")
	public String showProductDetails(@RequestParam("id") Long id, Optional<Product> product, Model model) {
		try {
			if (id != 0) {
				product = productService.getProductById(id);
				if (product.isPresent()) {
					model.addAttribute("id", product.get().getId());
					model.addAttribute("description", product.get().getDescription());
					model.addAttribute("name", product.get().getName());
					model.addAttribute("price", product.get().getPrice());
					return "productdetails";
				}
				return "redirect:/home";
			}
			return "redirect:/home";
		} catch (Exception e) {
			return "redirect:/home";
		}
	}

	
	@GetMapping("/product/show")
	public String show(Model map) {
		List<Product> products = productService.getAllActiveProducts();
		map.addAttribute("products", products);
		return "products";
	}

	@GetMapping("/product/display/{id}")
	@ResponseBody
	public void showImage(@PathVariable("id") Long id, HttpServletResponse response)
			throws ServletException, IOException {
		Optional<Product> product = productService.getProductById(id);
		if (product.isPresent()) {
			byte[] imageData = product.get().getImage();
			if (imageData != null && imageData.length > 0) {
				response.setContentType("image/jpeg, image/png");
				response.getOutputStream().write(imageData);
				response.getOutputStream().close();
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found for this product");
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
		}
	}

	@GetMapping("/productlist")
	public String viewProductList(Model model) 
	{
	    List<Product> products = productService.getAllActiveProducts();
	    
	    // Convert image byte[] to Base64 for each product
	    products.forEach(product -> 
	    {
	        if (product.getImage() != null) {
	            String base64Image = Base64.getEncoder().encodeToString(product.getImage());
	            product.setBase64Image(base64Image);  // Assuming you have a transient field to hold this
	        }
	    });
	    
	    model.addAttribute("products", products);
	    return "productlist";    
	}


	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
	    Product product = productService.getProduct(id);
	    
	  
	    if (product == null) {
	        return "redirect:/productlist";  
	    }

	
	    if (product.getImage() != null) {
	        String base64Image = Base64.getEncoder().encodeToString(product.getImage());
	        product.setBase64Image(base64Image);  
	    }

	    model.addAttribute("product", product);
	    return "update_product"; 
	}



	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable(value = "id") long id) {

	    productService.deleteProductById(id);
	     return "redirect:/productlist";
	}
	
	
	@GetMapping("/viewProduct/{id}")
	public String viewProduct(@PathVariable(value = "id") long id, Model model) {
	 
	    Product product = productService.getProduct(id);
	    
	        if (product.getImage() != null) {
	            String base64Image = Base64.getEncoder().encodeToString(product.getImage());
	            product.setBase64Image(base64Image);  // Assuming you have a transient field to hold this
	        }
	        model.addAttribute("product", product);
	    	return "view_product"; 
	}


	
	
}
