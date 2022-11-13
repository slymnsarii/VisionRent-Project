package com.visionrent.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.visionrent.domain.Car;
import com.visionrent.domain.ImageFile;
import com.visionrent.dto.CarDTO;
import com.visionrent.exception.BadRequestException;
import com.visionrent.exception.ConflictException;
import com.visionrent.exception.ResourceNotFoundException;
import com.visionrent.exception.message.ErrorMessage;
import com.visionrent.mapper.CarMapper;
import com.visionrent.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ImageFileService imageFileService;

	@Autowired
	private CarMapper carMapper;

	// *************************************

	public void saveCar(String imageId, CarDTO carDTO) {

		// image ID imageRepo da var mı ?
		ImageFile imageFile = imageFileService.findImageById(imageId);
		// imageId daha önce başka bir araç ile eşleşmiş mi
		Integer usedCarCount = carRepository.findCarCountByImageId(imageFile.getId());

		if (usedCarCount > 0) {
			throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
		}

		// mapper işlemi
		Car car = carMapper.carDTOToCar(carDTO);

		Set<ImageFile> imFiles = new HashSet<>();
		imFiles.add(imageFile);

		car.setImage(imFiles);

		carRepository.save(car);

	}

	// ********************************

	public List<CarDTO> getAllCars() {

		List<Car> carList = carRepository.findAll();
		return carMapper.map(carList);

	}

	// ***********************************

	public Page<CarDTO> findAllWithPage(Pageable pageable) {

		Page<Car> carPage = carRepository.findAll(pageable);
		Page<CarDTO> carPageDTO = carPage.map(new Function<Car, CarDTO>() {
			@Override
			public CarDTO apply(Car car) {
				return carMapper.carToCarDTO(car);
			}
		});
		return carPageDTO;
	}

	// *************************************
	public CarDTO findById(Long id) {

		Car car = getCar(id);

		return carMapper.carToCarDTO(car);

	}

	public Car getCar(Long id) {
		Car car = carRepository.findCarById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return car;
	}

	// *********************************************************************************

	public void updateCar(Long id, String imageId, CarDTO carDTO) {
		
		Car car = getCar(id);
		
		if(car.getBuiltIn()) { // builtIn kontrolu
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
		}
		
		ImageFile imageFile  =  imageFileService.findImageById(imageId);
		
		// burada amaç, verilen image daha önce başka araç için kullanılmış mı ?
		List<Car> carList = carRepository.findCarsByImageId(imageFile.getId());
		
		
		for(Car c: carList) {
			// bana gelen car Id si ile yukardakiList türündeki car Id leri eşit olmaları lazım,
			//eğer eşit değilse girilenm image başka bir araç için yüklenmiş
			if(car.getId().longValue()!=c.getId().longValue()) {
				throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
			}
			
		}
		
		car.setAge(carDTO.getAge());
		car.setAirConditioning(carDTO.getAirConditioning());
		car.setBuiltIn(carDTO.getBuiltIn());
		car.setDoors(carDTO.getDoors());
		car.setFuelType(carDTO.getFuelType());
		car.setLuggage(carDTO.getLuggage());
		car.setModel(carDTO.getModel());
		car.setPricePerHour(carDTO.getPricePerHour());
		car.setSeats(carDTO.getSeats());
		car.setTransmission(carDTO.getTransmission());
		
		car.getImage().add(imageFile);
		
		carRepository.save(car);

	}

	//******************** DELETE**********************
	
	public void removeById(Long id) {
		
		Car car = getCar(id);
		
		if(car.getBuiltIn()) { // builtIn kontrolu
			throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
		}
		
		carRepository.delete(car);
	
		
	}
	
	// ---> EKLENDİ
	public Car getCarById(Long id) {
		Car car= carRepository.findById(id).orElseThrow(()->new 
				ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
		return car;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
