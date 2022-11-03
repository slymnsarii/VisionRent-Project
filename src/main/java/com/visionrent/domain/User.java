package com.visionrent.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "t_user")
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50,nullable = false)
	private String firstName;
	
	@Column(length = 50,nullable = false)
	private String lastName;
	
	@Column(length = 80,nullable = false,unique = true)
	private String email;
	
	@Column(length = 120,nullable = false)
	private String password;
	
	@Column(length = 14,nullable = false)
	private String phoneNumber;
	
	@Column(length = 100,nullable = false)
	private String address;
	
	@Column(length = 15,nullable = false)
	private String zipCode;
	
	@Column(nullable = false)
	private Boolean builtIn=false; //silinmesi ve degistirilmesi istenmeyen obje
	
	@ManyToMany //hibernate default'ta LAZZY
	@JoinTable(name = "t_user_role",
			   joinColumns = @JoinColumn(name="user_id"),
			   inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roles=new HashSet<>();

}
