package com.visionrent.mapper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.visionrent.domain.Car;
import com.visionrent.domain.ImageFile;
import com.visionrent.dto.CarDTO;
@Mapper(componentModel = "spring")
public interface CarMapper {
	
	
	@Mapping(target="image" , ignore = true) // image fieldı bir tarafda String diğer tarafda ImageFile türünde old için ignore ediliyor
	Car carDTOToCar(CarDTO carDTO) ;
	
	//*************************************
	
	 List<CarDTO> map(List<Car> cars);
	
	@Mapping(source="image", target="image", qualifiedByName = "getImageAsString")
	CarDTO carToCarDTO(Car car);
	
	@Named("getImageAsString")
	public static  Set<String> getImageIds( Set<ImageFile> imageFiles) {
		Set<String> imgs = new HashSet<>();
		imgs = imageFiles.stream().map(imFile->imFile.getId().
																											toString()).
																										    collect(Collectors.toSet());
		 return imgs;
	}
}