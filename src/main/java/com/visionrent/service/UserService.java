package com.visionrent.service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.visionrent.domain.Role;
import com.visionrent.domain.User;
import com.visionrent.domain.enums.RoleType;
import com.visionrent.dto.UserDTO;
import com.visionrent.dto.request.RegisterRequest;
import com.visionrent.exception.ConflictException;
import com.visionrent.exception.ResourceNotFoundException;
import com.visionrent.exception.message.ErrorMessage;
import com.visionrent.mapper.UserMapper;
import com.visionrent.repository.UserRepository;
import com.visionrent.security.SecurityUtils;
@Service
public class UserService {
	
	
	private UserRepository userRepository;
	
	
	private RoleService roleService ;
	
	private PasswordEncoder passwordEncoder;
	
	private UserMapper userMapper;
	
	
	
	@Autowired
	public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder, UserMapper userMapper) {
		super();
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
	}
	public User getUserByEmail(String email ) {
		
		  User user  =  userRepository.findByEmail(email).orElseThrow(()->
		  			new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email))
				);
		return user ;
	}
	public void saveUser(RegisterRequest registerRequest) {
		if(userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,
																											registerRequest.getEmail()));
		}
		
		Role role = roleService.findByType(RoleType.ROLE_CUSTOMER);
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		
		String encodedPassword =  passwordEncoder.encode(registerRequest.getPassword());
		
		User user = new User();
		
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodedPassword);
		user.setPhoneNumber(registerRequest.getPhoneNumber());
		user.setAddress(registerRequest.getAddress());
		user.setZipCode(registerRequest.getZipCode());
		user.setRoles(roles);
		
		userRepository.save(user);
		
		
		
	}
	public List<UserDTO> getAllUsers() {
		
		    List<User> users = userRepository.findAll();
		     List<UserDTO> userDTOs = userMapper.map(users);
		    
		     return userDTOs;
		
	}
	public UserDTO getPrincipal() {
		 User currentUser =  getCurrentUser();
		  // return userMapper.userToUserDTO(currentUser);
		  UserDTO userDTO = userMapper.userToUserDTO(currentUser);
		  return userDTO;
	
		
	}
	
	public User getCurrentUser() {
		
		String email = SecurityUtils.getCurrentUserLogin().orElseThrow(()->
		 new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
		User user =  getUserByEmail(email);
		return user ;
		
	}
	
	/*  BURA ANLATILACAK
	 *
	public Page<UserDTO> getUserPage(Pageable pageable) {
		Page<User> userPage = userRepository.findAll(pageable);
		Page<UserDTO> userPageDTO = userPage.map(userMapper::userToUserDTO);
		return userPageDTO;
	}
	*/
	
	public Page<UserDTO> getUserPage(Pageable pageable) {
		   Page<User> userPage = userRepository.findAll(pageable);
		  
	        return getUserDTOPage(userPage);
	
	}
	
	private Page<UserDTO> getUserDTOPage(Page<User> userPage) {
		
		 Page<UserDTO> userDTOPage =  userPage.map(new Function<User, UserDTO>() {
			 @Override
			public UserDTO apply(User user) {
				
				return userMapper.userToUserDTO(user);
			}
		});
		
		 return userDTOPage;
		
	}
	public UserDTO getUserById(Long id) {
		    User user = userRepository.findById(id).orElseThrow(()->
		  
				   new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		    return userMapper.userToUserDTO(user);
	}
	
	
	
}