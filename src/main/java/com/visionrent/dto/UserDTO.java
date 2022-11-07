package com.visionrent.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.visionrent.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String phoneNumber;
	
	private String address;
	
	private String zipCode;
	
	private Boolean builtIn = false; // silinmesini ve değiştirilmesi istenmeyen obje..
	
	private  Set<String> roles;
	
	public void setRoles(Set<Role> roles) {
		
		Set<String> roleStr=new HashSet<>();
		
		roles.forEach(r->{
			roleStr.add(r.getType().getName()); //Administrator veya Customer gozukecek
		});
		this.roles=roleStr;
	}
}
