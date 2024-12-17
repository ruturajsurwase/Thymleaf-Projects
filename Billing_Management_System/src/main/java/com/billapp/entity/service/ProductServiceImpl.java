package com.billapp.entity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billapp.entity.Product;
import com.billapp.repository.ProductRepository;

@Service
public class ProductServiceImpl  implements ProductService {

	    @Autowired
	    private ProductRepository productRepository;

	
	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) 
	{
		 return productRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
	}

	@Override
	public void updateProduct(Long id,Product product) {
		 productRepository.save(product); // Save the updated product
	}

	@Override
	public void deleteProductById(Long id) {
		  productRepository.deleteById(id);
		
	}

	@Override
	public List<Product> findByNameContainingIgnoreCase(String query) {
		 return productRepository.findByNameContainingIgnoreCase(query);
	}

	@Override
	public Product updateStock(Long id, int newStock) 
	{
		Product product = getProductById(id);
        product.setStockQuantity(newStock);
        return productRepository.save(product);
	}

	
	
}
