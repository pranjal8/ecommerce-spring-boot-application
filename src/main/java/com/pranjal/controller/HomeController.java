package com.pranjal.controller;

import java.rmi.ServerException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pranjal.global.GlobalData;
import com.pranjal.model.User;
import com.pranjal.service.CategoryService;
import com.pranjal.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
		
	@GetMapping( {"/", "/home"} )
	public String home(Model model) {	
		//for cart count
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}
	
	@GetMapping( "/shop" )
	public String shop(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		
		//for cart count
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		return "shop";
	}
	@GetMapping( "/shop/category/{id}" )
	public String shopByCategory(@PathVariable int id, Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		
		//for cart count
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		return "shop";
	}
	
	@GetMapping( "/shop/viewproduct/{id}" )
	public String viewProductDetails(@PathVariable int id, Model model) {
		model.addAttribute("product", productService.getProductById(id).get());
		
		//for cart count
		model.addAttribute("cartCount", GlobalData.cart.size());
		
		return "viewProduct";
	}
	
}
