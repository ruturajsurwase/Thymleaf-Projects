package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public void saveProduct(Product product) {
		productRepository.save(product);	
	}

	@Override
	public List<Product> getAllActiveProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(Long id) {
		  Optional<Product> optional = productRepository.findById(id);
	        Product product = null;
	        if (optional.isPresent()) 
	        {
	            product = optional.get();
	        } else 
	        {
	            throw new RuntimeException("Product not found for id :: " + id);
	        }
	        return product;
	}

	@Override
	public void deleteProductById(long id) 
	{
		productRepository.deleteById(id);
		
	}

	@Override
	public Optional<Product> getProductById(long id )
	{
		Optional<Product> byId = productRepository.findById(id);
		return byId;
	}


}
