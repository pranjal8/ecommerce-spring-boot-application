package com.pranjal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pranjal.model.Category;
import com.pranjal.model.Product;
import com.pranjal.repository.ProductRepository;

@Service
public class ProductService  {
	@Autowired
	ProductRepository productRepo;
	
	       //get list of products
			public List<Product> getAllProduct(){
				return productRepo.findAll();
			}

			//Add 
			public void addProduct(Product product) {
				productRepo.save(product);
			}
			
			//delete product by id
			public void removeProductById(long id) {
				productRepo.deleteById(id);
			}
		
			//used for update method
			//get optional product by id
	        public Optional<Product> getProductById(long id) {
	        	return productRepo.findById(id);      	
	        }
	      
	        //get product by category id
	        public List<Product> getAllProductsByCategoryId(int id){
	        	
	        	return productRepo.findAllByCategoryId(id);
	        }
	        
	        
			
}
