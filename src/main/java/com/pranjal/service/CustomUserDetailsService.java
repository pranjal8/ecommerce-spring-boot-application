package com.pranjal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pranjal.model.CustomUserDetails;
import com.pranjal.model.User;
import com.pranjal.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findUserByEmail(username);  //username is email here
		user.orElseThrow(() -> new UsernameNotFoundException("user toh nahi mila"));
		return user.map(CustomUserDetails :: new).get();
	}

}
