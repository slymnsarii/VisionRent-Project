package com.visionrent.controller;

import javax.validation.Valid;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.visionrent.dto.request.LoginRequest;
import com.visionrent.dto.request.RegisterRequest;
import com.visionrent.dto.response.LoginResponse;
import com.visionrent.dto.response.ResponseMessage;
import com.visionrent.dto.response.VRResponse;
import com.visionrent.security.jwt.JwtUtils;
import com.visionrent.service.UserService;

@RestController
public class UserJwtController {
	
	// Bu classımda sadece login ve register işlemleri yapılacak
	
   @Autowired
   private UserService userService;
   
   @Autowired
   private AuthenticationManager authenticationManager;
   
   @Autowired
   private JwtUtils  jwtUtils;
   
   // register
   @PostMapping("/register")
   public ResponseEntity<VRResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest  )  {
	   userService.saveUser(registerRequest);
	   
	   VRResponse response = new VRResponse();
	   response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
	   response.setSuccess(true);
	   
	   return new ResponseEntity<>(response,HttpStatus.CREATED);
	   
   }
   
   
   // login
   @PostMapping("/login")
   public ResponseEntity<LoginResponse> authenticate( @Valid @RequestBody LoginRequest loginRequest    )  {
	   
	   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
			     new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
	   
	    Authentication authentication  =  authenticationManager.
	    		       authenticate(usernamePasswordAuthenticationToken);
	    
	 UserDetails userDetails  =  (UserDetails) authentication.getPrincipal() ;// mevcut giriş yapan kullanıcıyı getirir
	    
	   String jwtToken =   jwtUtils.generateJwtToken(userDetails);
	   
	   LoginResponse loginResponse = new LoginResponse(jwtToken);
	   
	   return new ResponseEntity<>(loginResponse,HttpStatus.OK);
	   
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
}
