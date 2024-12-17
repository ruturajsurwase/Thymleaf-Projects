package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Product;

public interface ProductService {

	
	public void saveProduct(Product product);

	  public   List<Product> getAllActiveProducts();

	  public  Product getProduct(Long id);

	public void deleteProductById(long id);
	
    public Optional<Product>getProductById(long id);
}

