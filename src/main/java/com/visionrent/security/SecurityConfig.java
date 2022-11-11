package com.visionrent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.visionrent.security.jwt.AuthTokenFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // method bazlı çalışacağım
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
    @Bean
	public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception{ 
    	http.csrf().disable().
    	          sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
    	          and().
    			  authorizeRequests().
    			  antMatchers("/",
    					  						"index.html", 
    					  						"/login", 
    					  						"/register",
    					  						"/js/*",
    					  						"/css/*",
    					  						"/images/*",
    					  						"/files/download/**",
    					  						"/files/display/**",
    					  						"/car/visitors/**").permitAll().
    			  anyRequest().authenticated();
    	
    	http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    	return http.build();
    	    	
    }
    
    @Bean
    public AuthTokenFilter authTokenFilter() {
    	return new AuthTokenFilter();
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(10);
    }
    
    @Bean
    public DaoAuthenticationProvider authProvider() {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setUserDetailsService(userDetailsService);
    	authenticationProvider.setPasswordEncoder(passwordEncoder());
    	
    	return authenticationProvider;
    	
    }
    
    @Bean
    public AuthenticationManager authManager( HttpSecurity http) throws Exception {
    	
    	return http.getSharedObject(AuthenticationManagerBuilder.class).
    								authenticationProvider(authProvider() ).
    								build();
    	
    }
    
    

	
	
	
	

}
