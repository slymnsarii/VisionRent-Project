package com.visionrent.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="t_car")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  
	
	@Column(length = 30, nullable = false)
	private String model;
	
	@Column(nullable = false)
	private Integer doors;
	
	@Column(nullable = false)
	private Integer seats;
	
	@Column(nullable = false)
	private Integer luggage;
	
	@Column(length = 30, nullable = false)
	private String  transmission;
	
	@Column(nullable = false)
	private Boolean airConditioning;
	
	@Column(nullable = false)
	private Integer age;
	
	@Column(nullable = false)
	private Double pricePerHour;
	
	@Column(length = 30, nullable = false)
	private String fuelType;
	
	
	private Boolean builtIn = false;
	
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name="car_id")
	private Set<ImageFile> image;
	

}
