package com.visionrent.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.visionrent.domain.User;
import com.visionrent.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserDTO userToUserDTO(User user);
	
	List<UserDTO> map(List<User> userList);
	
}
