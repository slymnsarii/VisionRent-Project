package com.visionrent.service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.visionrent.domain.Car;
import com.visionrent.domain.ImageFile;
import com.visionrent.dto.CarDTO;
import com.visionrent.exception.ConflictException;
import com.visionrent.exception.message.ErrorMessage;
import com.visionrent.mapper.CarMapper;
import com.visionrent.repository.CarRepository;
@Service
public class CarService {
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private ImageFileService imageFileService ;
	
	@Autowired
	private CarMapper carMapper;
	public void saveCar(String imageId, CarDTO carDTO) {
		
		// image ID imageRepo da var mı ?
		ImageFile imageFile =  imageFileService.findImageById(imageId);
		// imageId daha önce başka bir araç ile eşleşmiş mi
		    Integer usedCarCount =  carRepository.findCarCountByImageId(imageFile.getId());
		   
		    if(usedCarCount>0) {
		    	throw new ConflictException(ErrorMessage.IMAGE_USED_MESSAGE);
		    }
		   
		    // mapper işlemi
		    Car car =  carMapper.carDTOToCar(carDTO);
		   
		    Set<ImageFile> imFiles = new HashSet<>();
		    imFiles.add(imageFile);
		   
		    car.setImage(imFiles);
		   
		    carRepository.save(car);
	}
	//********************************
	
	public List<CarDTO> getAllCars() {
		
	List<Car>	carList = carRepository .findAll();
    return carMapper.map(carList);
		
		
	}
public Page<CarDTO> findAllWithPage(Pageable pageable) {
		
		Page<Car> carPage  = carRepository.findAll(pageable);
		Page<CarDTO> carPageDTO =  carPage.map(new Function<Car,CarDTO>(){
			@Override
			public CarDTO apply(Car car) {
				return carMapper.carToCarDTO(car);
			}
		});
		return carPageDTO;
	}
	
	
}