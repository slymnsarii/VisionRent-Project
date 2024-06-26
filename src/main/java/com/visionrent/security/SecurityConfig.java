package com.visionrent.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
    	          and(). // Cors işlemleri (delete) için alttaki 2 satır eklendi
    	          authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/**").permitAll().and().
    			  authorizeRequests().
    			  antMatchers(
    					  						"/login",
    					  						"/register",
    					  						"/files/download/**",
    					  						"/contactmessage/visitors",
    					  						"/files/display/**",
    					  						"/car/visitors/**",
    					  						"/actuator/info","/actuator/health").permitAll().
    			  anyRequest().authenticated();
    	
    	http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    	return http.build();
    	    	
    }
    //*************** cors Ayarları ****************************
    @Bean
   	public WebMvcConfigurer corsConfigurer() {
   		return new WebMvcConfigurer() {
   			@Override
   			public void addCorsMappings(CorsRegistry registry) {
   				registry.addMapping("/**").allowedOrigins("*"). //"http:127.0.0.1/8080 diye spesific adresden gelenleri kabul et de diyebiliriz
   																			allowedHeaders("*").
   																			allowedMethods("*");
   			}
   		};
   	}
    //*******************SWAGGER***********************
    private static final String [] AUTH_WHITE_LIST= {
			"/v3/api-docs/**", // swagger
			"swagger-ui.html", //swagger
			"/swagger-ui/**", // swagger
			"/",
			"index.html",
			"/images/**",
			"/css/**",
			"/js/**"
	};
	// yukardaki static listeyi de giriş izni veriyoruz, boiler plate
    @Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		WebSecurityCustomizer customizer=new WebSecurityCustomizer() {
			@Override
			public void customize(WebSecurity web) {
				web.ignoring().antMatchers(AUTH_WHITE_LIST);
			}
		};
		return customizer;
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