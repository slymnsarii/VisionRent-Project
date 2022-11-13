package com.visionrent.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.visionrent.domain.ImageFile;
import com.visionrent.domain.Reservation;
import com.visionrent.domain.User;
import com.visionrent.dto.ReservationDTO;
import com.visionrent.dto.request.ReservationRequest;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
	
	Reservation reservationRequestToReservation(ReservationRequest reservationRequest);
	
	// *******************************************************************
	
	@Mapping(source="car.image", target="car.image", qualifiedByName = "getImageAsString")
	@Mapping(source="user", target="userId", qualifiedByName = "getUserId")
	ReservationDTO reservationToReservationDTO(Reservation reservation);
	
	List<ReservationDTO> map(List<Reservation> reservationList);
	

		@Named("getImageAsString")
		public static Set<String> getImageIds(Set<ImageFile> imageFiles) {
			
			 Set<String> imgs = new HashSet<>();
			 imgs = imageFiles.stream().map(imFile->imFile.getId().toString()).collect(Collectors.toSet());
			 return imgs;
		}
		
		@Named("getUserId")
		public static Long getUserId(User user) {
			return user.getId();
		}
}
