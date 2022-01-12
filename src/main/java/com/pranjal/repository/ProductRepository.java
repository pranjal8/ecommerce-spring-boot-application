package com.pranjal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranjal.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	
	List<Product> findAllByCategoryId(int id);

	
}
