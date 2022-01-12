package com.pranjal.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pranjal.dto.ProductDTO;
import com.pranjal.model.Category;
import com.pranjal.model.Product;
import com.pranjal.service.CategoryService;
import com.pranjal.service.ProductService;

@Controller
public class AdminController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	//handler to open  "adminHome.html" page
	@GetMapping("/admin")
	public String adminHome() {		
		return "adminHome";
	}
	
	//handler get all categories to open  "categories.html" page
   @GetMapping("/admin/categories")
    public String getCategories(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "categories";
	}
  
   //handler to open  "categoriesAdd.html" page
   @GetMapping("/admin/categories/add")
    public String getCategoriesAdd(Model model) {
		model.addAttribute("category", new Category() );
		System.out.println("blank Category Object => " + new Category());
		return "categoriesAdd";
	}

   public static String uploadDir =System.getProperty("user.dir") +"/src/main/resources/static/productImages";
   
       //handler to add  new category in DB	
	  @PostMapping("/admin/categories/add") 
	  public String addCategories(@ModelAttribute("category") Category category) {
	  categoryService.addCategory(category); 
	  return "redirect:/admin/categories";
	  }
	  
	  //delete category
	  
	  //handler to delete category by id
	  @GetMapping("/admin/categories/delete/{id}")
	  public String deleteCategory(@PathVariable int id) {		
		  categoryService.removeCategoryById(id);		  
		  return "redirect:/admin/categories";		  
	  }
	  
	//handler to update category by id
	  @GetMapping("/admin/categories/update/{id}")
	  public String updateCategory(@PathVariable int id, Model model) {
		  
		  Optional<Category> category = categoryService.getCategoryById(id);
		  System.out.println("Update caegory =>" + category);
		
		  if(category.isPresent()) {
			  System.out.println(category.get());
			  model.addAttribute("category", category.get());
			  return "categoriesAdd";
		  } else return "404";	
	  }
	  	 	
	  //product section....
	  //get products 
	  @GetMapping("/admin/products")
	  public String getProduct(Model model) {
		  model.addAttribute("products", productService.getAllProduct());
		  System.out.println("Products =>" +  productService.getAllProduct());
		  return "products";
	  }
	  
	//handler to open  "productsAdd.html" page
	   @GetMapping("/admin/products/add")
	    public String getProductsAddPage(Model model) {
			model.addAttribute("productDTO", new ProductDTO() );
			System.out.println("blank ProductDTO Object => " +new ProductDTO() );
			
			model.addAttribute("categories", categoryService.getAllCategory());		
			return "productsAdd";
		}
	   
	   //handler to add product in DB
	   @PostMapping("/admin/products/add")
	   public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO ,
			   @RequestParam("productImage") MultipartFile file, @RequestParam("imgName") String imgName ) throws IOException{
		
		   Product product=new Product();
		   product.setId(productDTO.getId());
		   product.setName(productDTO.getName());
		   product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		   product.setPrice(productDTO.getPrice());
		   product.setWeight(productDTO.getWeight());
		   product.setDescription(productDTO.getDescription());
		   
		   String imageUUID;
		   if(!file.isEmpty()) {
			   imageUUID= file.getOriginalFilename();
			 
			Path fileNameAndPath= Paths.get( uploadDir,imageUUID);
			   Files.write(fileNameAndPath,file.getBytes());
		   } else {
			   imageUUID= imgName;
		   }
		   product.setImageName(imageUUID);
		   
		   productService.addProduct(product);
		   
		   return "redirect:/admin/products";	   
	   }
	 

		  
		  //handler to delete product by id
		  @GetMapping("/admin/product/delete/{id}")
		  public String deleteProduct(@PathVariable long id) {		
			  productService.removeProductById(id);		  
			  return "redirect:/admin/products";		  
		  } 
	    
		  //handler to update product
		  @GetMapping("/admin/product/update/{id}")
		  public String updateProduct(@PathVariable long id, Model model) {
			  
			  Product product = productService.getProductById(id).get();
			  if(product  != null) {
				  ProductDTO productDto = new ProductDTO();
				  
				  productDto.setId(product.getId());
				  productDto.setName(product.getName());
				  productDto.setCategoryId(product.getCategory().getId());
				  productDto.setPrice(product.getPrice());
				  productDto.setWeight(product.getWeight());
				  productDto.setDescription(product.getDescription());
				  productDto.setImageName(product.getImageName());
				  
				  model.addAttribute("categories", categoryService.getAllCategory());
				  model.addAttribute("productDTO", productDto);
							  
			  }else {
				  throw new IllegalArgumentException();
			  }
			 			  
				  return "productsAdd";
		  }
		  		  
}














