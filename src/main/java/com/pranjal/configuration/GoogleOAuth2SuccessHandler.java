package com.pranjal.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.pranjal.model.Role;
import com.pranjal.model.User;
import com.pranjal.repository.RoleRepository;
import com.pranjal.repository.UserRepository;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	//RedirectStrategy used to redirect interally
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	// google auth
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// authentication is a token

		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();

		if (!userRepo.findUserByEmail(email).isPresent()) {

			User user = new User();
			user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
			user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
			user.setEmail(email);
			
			List<Role> roles = new ArrayList<>();
			//at id num 1 ==> userrole =>ADMIN
			// at id num 2 ==> userrole =>USER
			roles.add(roleRepo.findById(2).get());
			
			user.setRoles(roles);
			userRepo.save(user);
		}

		//	redirectStrategy.sendRedirect(request, response, "redirection_path");
		// here "redirection_path" is home
		redirectStrategy.sendRedirect(request, response, "/");
	}

}
