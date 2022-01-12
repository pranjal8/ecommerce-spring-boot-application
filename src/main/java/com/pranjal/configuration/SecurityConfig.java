package com.pranjal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pranjal.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	//step 1
	//configuration
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//route, login , google login (OAuth) , logout, remove cookies , exception handling , csrf disable
		http.authorizeRequests()
			.antMatchers("/", "/shop/**", "/register").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			.and().formLogin()
			.loginPage("/login").permitAll()
			.failureUrl("/login?error =true")
			.defaultSuccessUrl("/")
			.usernameParameter("email")
			.passwordParameter("password")
			.and().oauth2Login()
			.loginPage("/login")
			.successHandler(googleOAuth2SuccessHandler)
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.and().exceptionHandling()
			.and().csrf().disable();
				
		 //use this line when use H2 DB
		// http.headers().frameOptions().disable();		
	}
	
	//step 2
	//password encoder
	//bean for BCryptPasswordEncoder			
	   @Bean
		public BCryptPasswordEncoder bCryptPasswordEncoder() {
			return new BCryptPasswordEncoder();			
		}

	    //step 3
		//authenticate user by it's credentials
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(customUserDetailsService);
		}

		//step 4
		//ignore all route of static folders
		@Override
		public void configure(WebSecurity web) throws Exception {
			// Security check for static content
			web.ignoring().antMatchers("/resources/**" , "/static/**" , "/css/**" , "/images/**" , "/js/**"  , "/productImages/**");
		}
		
		
		
		
	
	
	
	

}
