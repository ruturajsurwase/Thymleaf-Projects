package com.billapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.billapp.entity.Product;
import com.billapp.entity.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/addproduct")
    public String showAddProductForm(Model model) 
    {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,RedirectAttributes redirectAttributes) 
    {
        productService.saveProduct(product);
        redirectAttributes.addFlashAttribute("msg", "Product saved successfully");
        return "redirect:/products/list";
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }
    
    
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id); 
        model.addAttribute("product", product);          
        return "update-product";                           
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, Model model) {
        productService.updateProduct(id, product);
        model.addAttribute("msg", "Product updated successfully");
        return "redirect:/products/list";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProductById(id);                                      // Delete the product by ID
        redirectAttributes.addFlashAttribute("msg", "Product deleted successfully");
        return "redirect:/products/list";
    }

    
    @GetMapping("/search")
    @ResponseBody
    public List<Product> searchProducts(@RequestParam("query") String query)
    {
        return productService.findByNameContainingIgnoreCase(query);
    }
    
    @GetMapping("/stock")
    public String viewStockPage(Model model)
    {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "stock-management";
    }

    @PostMapping("/update-stock/{id}")
    public String updateStock(@PathVariable Long id, @RequestParam("newStock") int newStock, Model model,RedirectAttributes redirectAttributes) 
    {
        productService.updateStock(id, newStock);
        redirectAttributes.addFlashAttribute("msg", "Product Stock updated successfully");
        return "redirect:/products/stock";
    }
    
  
	/*
	 * @GetMapping("/paginated") public String
	 * listPaginatedProducts(@RequestParam(defaultValue = "0") int page,
	 * 
	 * @RequestParam(defaultValue = "5") int size, Model model) { Page<Product>
	 * productPage = productService.findAll(PageRequest.of(page, size));
	 * model.addAttribute("productPage", productPage); return "product-list"; }
	 */
    
    
}