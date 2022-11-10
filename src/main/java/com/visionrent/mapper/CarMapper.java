package com.visionrent.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.visionrent.domain.Car;
import com.visionrent.dto.CarDTO;

@Mapper(componentModel = "spring")
public interface CarMapper {

	@Mapping(target = "image", ignore = true) //image field'i bir tarafta String, 
											 //diger tarafta ImageFile turunde oldugu icin ignore ediliyor
	Car carDTOtoCar(CarDTO carDTO);
	
	//***********************
	
	//TODO bakilacak
	//List<CarDTO>map(List<Car>car);
	
	//TODO bakilacak
	CarDTO carToCarDTO(Car car);
	
}
