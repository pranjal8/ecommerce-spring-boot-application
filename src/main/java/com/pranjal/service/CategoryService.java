package com.pranjal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pranjal.model.Category;
import com.pranjal.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepo;
	
	//Add 
	public void addCategory(Category category) {
		categoryRepo.save(category);
	}
	
	//get list of categories
		public List<Category> getAllCategory(){
			return categoryRepo.findAll();
		}
		
		//delete calegory by id
		public void removeCategoryById(int id) {
			categoryRepo.deleteById(id);
		}
	
		//get optional category by id
        public Optional<Category> getCategoryById(int id) {
        	return categoryRepo.findById(id);      	
        }
				
}
