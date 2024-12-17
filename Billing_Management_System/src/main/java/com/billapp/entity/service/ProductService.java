package com.billapp.entity.service;

import java.util.List;




import com.billapp.entity.Product;

public interface ProductService {

	
	
	public Product saveProduct(Product product);
	 public List<Product> getAllProducts();
	public Product getProductById(Long id);
	public void updateProduct(Long id,Product product);
	public void deleteProductById(Long id);
	public List<Product> findByNameContainingIgnoreCase(String query);
	public Product updateStock(Long id, int newStock);
	/* public Page<Product> findAll(PageRequest of); */
	
}
