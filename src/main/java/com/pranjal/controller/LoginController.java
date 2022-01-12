package com.pranjal.controller;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pranjal.global.GlobalData;
import com.pranjal.model.Role;
import com.pranjal.model.User;
import com.pranjal.repository.RoleRepository;
import com.pranjal.repository.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	//open login page
	@GetMapping("/login")
	public String login() {
		
		//clear cart
		GlobalData.cart.clear();
		
		return "login";
	}
	
	//open register form
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	//registration
		@PostMapping("/register")
		public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServerException {
			/*Used HttpServletRequest request here for redirect user to system after registered 
			 *  (do auto login after registered)
			 *  */
			
			String password = user.getPassword();
			user.setPassword(bCryptPasswordEncoder.encode(password));
			
			List<Role> roles = new ArrayList<>();
			//at id num 1 ==> userrole =>ADMIN
			// at id num 2 ==> userrole =>USER
			roles.add(roleRepo.findById(2).get());		
			user.setRoles(roles);
			
			userRepo.save(user);
						
			try {
			//  (automatic login after successfully registered)
				request.login(user.getEmail(), password);
			} catch (ServletException e) {
				System.out.println("Error = " +e);
				e.printStackTrace();
			}
			
			return "redirect:/";
			
		}
	
		
}
