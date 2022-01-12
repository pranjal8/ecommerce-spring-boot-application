package com.pranjal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranjal.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
