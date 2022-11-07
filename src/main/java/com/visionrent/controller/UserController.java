package com.visionrent.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.visionrent.dto.ContactMessageDTO;
import com.visionrent.dto.UserDTO;
import com.visionrent.service.UserService;
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// getAllUser
	@GetMapping("/auth/all")
	@PreAuthorize("hasRole('ADMIN')")  // hasRoel metodu 'ROLE_' kendisi ekliyor
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<UserDTO> allUsers = userService.getAllUsers();
		
		return ResponseEntity.ok(allUsers);
		
	}
	
	// sisteme giren kullanıcı bilgilerini getiren method
	// gririş yapan kullanıcının profil saysafında  kendi bilgilerini göstermek için
	@GetMapping
	@PreAuthorize( "hasRole('ADMIN') or hasRole('CUSTOMER')  " )
	public ResponseEntity<UserDTO> getUser() {
		   UserDTO userDTO =  userService.getPrincipal();
		  
		   return ResponseEntity.ok(userDTO);
		  
	}
	
	
	// getAllUsersByPage
	@GetMapping("/auth/pages")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<UserDTO>> getAllUsersByPage(
																						 @RequestParam("page") int page,
																						 @RequestParam("size") int size,
																						 @RequestParam("sort") String prop,//neye göre sıralanacağı belirtiliyor
																						 @RequestParam(value="direction",
																								 						required = false, // direction required olmasın
																								 						defaultValue = "DESC") Direction direction )  {
		    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
		
		    Page<UserDTO> userDTOPage  =   userService.getUserPage(pageable);
		   
		    return ResponseEntity.ok(userDTOPage);
		
	}
	
	
	// getUserById
	
	@GetMapping( "/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> getUserById( @PathVariable Long id   ) {
		  UserDTO userDTO  = userService.getUserById(id);
		 
		  return ResponseEntity.ok(userDTO);
		
	}
	
	
	
	
	
}