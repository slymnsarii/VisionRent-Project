package com.visionrent.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.visionrent.domain.Car;
import com.visionrent.domain.User;
import com.visionrent.dto.ReservationDTO;
import com.visionrent.dto.request.ReservationRequest;
import com.visionrent.dto.request.ReservationUpdateRequest;
import com.visionrent.dto.response.CarAvailabilityResponse;
import com.visionrent.dto.response.ResponseMessage;
import com.visionrent.dto.response.VRResponse;
import com.visionrent.service.CarService;
import com.visionrent.service.ReservationService;
import com.visionrent.service.UserService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private UserService userService;
	
	//******* Make Reservation ************************************
	
  //localhost:8080/reservation/add?carId=1
	
	@PostMapping("/add")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('CUSTOMER')  " )
	public ResponseEntity<VRResponse> makeReservation(@RequestParam("carId") Long carId,
																														  @Valid @RequestBody ReservationRequest reservationRequest) {
		  Car car =  carService.getCarById(carId);  
		  // login
		  User user  =userService.getCurrentUser();
		  
		  reservationService.createReservation(reservationRequest,user,car);
		  
		 VRResponse response = new VRResponse(ResponseMessage.RESERVATION_CREATED_RESPONSE_MESSAGE,true);
		 
		 return new ResponseEntity<>(response,HttpStatus.CREATED);
		 
	}
	
	//********************AdminMakeReservation****************************
	
	@PostMapping("/add/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VRResponse> addReservation(@RequestParam("userId") Long userId,
																														@RequestParam("carId") Long carId,
																														@Valid @RequestBody ReservationRequest reservationRequest) {
		 Car car =  carService.getCarById(carId);  
		User user =  userService.getById(userId);
		
		reservationService.createReservation(reservationRequest, user, car);
		
		VRResponse response = new VRResponse(ResponseMessage.RESERVATION_CREATED_RESPONSE_MESSAGE, true);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
		
		
	}
	
	//***********************getAllReservations*******************************************
	
	@GetMapping("/admin/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getAllReservations() {
		
		List<ReservationDTO> allReservations = reservationService.getAllReservations();
		
		return ResponseEntity.ok(allReservations);
	}
	
	//*********************getAllReservationsWithPage*********************************************************
	
	@GetMapping("/admin/all/pages")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<ReservationDTO>> getAllReservationsWithPage(@RequestParam("page") int page,
																																											@RequestParam("size") int size,
																																											@RequestParam("sort") String prop,//neye göre sıralanacağı belirtiliyor
																																											@RequestParam(value="direction",
																																																	required = false, // direction required olmasın
																																																	defaultValue = "DESC") Direction direction )  {
				Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
				 Page<ReservationDTO> allReservations = reservationService.getReservationPage(pageable);
				 
				 return  ResponseEntity.ok(allReservations);

		
		
	}
	
	//************************CheckCarIsAvailable************************************
	
	@GetMapping("/auth")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('CUSTOMER')  " )
	public ResponseEntity<VRResponse> checkCarIsAvailable (
																	@RequestParam("carId") Long carId,
																	@RequestParam("pickUpDateTime") 
																				@DateTimeFormat(pattern="MM/dd/yyyy HH:mm:ss") LocalDateTime pickUpTime,
																	@RequestParam("dropOffDateTime") 
																	 			@DateTimeFormat(pattern="MM/dd/yyyy HH:mm:ss") LocalDateTime dropOffTime) {
		
		
		   Car car =carService.getCarById(carId);
		   boolean isAvailable  = reservationService.checkCarAvailability(car, pickUpTime, dropOffTime);
		   
		   Double totalPrice = reservationService.getTotalPrice(car, pickUpTime, dropOffTime);
		   
		   VRResponse response = new CarAvailabilityResponse(ResponseMessage.CAR_AVAILABLE_MESSAGE, true, isAvailable, totalPrice);
		   
		   return ResponseEntity.ok(response);
	}
	
	//***********************UpdateReservation**************************************************************************
	@PutMapping("/admin/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VRResponse> updateReservation(
																															@RequestParam("carId") Long carId,
																															@RequestParam("reservationId") Long reservationId,
																															@Valid @RequestBody ReservationUpdateRequest reservationUpdateRequest)    {
		Car car = carService.getCarById(carId);
		reservationService.updateReservation(reservationId,car,reservationUpdateRequest);
		
		VRResponse response = new VRResponse(ResponseMessage.RESERVATION_UPDATED_RESPONSE_MESSAGE, true);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	//**************************getReservationByIdADMIN*************************************************************************************
	
	@GetMapping("/{id}/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
		   ReservationDTO reservationDTO = reservationService.getReservationDTO(id);
		   return ResponseEntity.ok(reservationDTO);
	}
	
	//***************************getReservationsForSpesificUser**********************************************************
	
	@GetMapping("/admin/auth/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<ReservationDTO>> getAllUserReservations (
			@RequestParam("userId") Long userId,
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam("sort") String prop,//neye göre sıralanacağı belirtiliyor
			@RequestParam(value="direction",
									required = false, // direction required olmasın
									defaultValue = "DESC") Direction direction )  {
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
		
		 User user = userService.getById(userId);
		    Page<ReservationDTO> reservationDTOPage =  reservationService.findReservationPageByUser(user,pageable);
		    
		    return ResponseEntity.ok(reservationDTOPage);
		
	}
	
	//***********Customer veya Admin kendine ait olan reservasyon bilgilerini getirsin**********************************
	
	@GetMapping("/{id}/auth")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('CUSTOMER')  " )
	public ResponseEntity<ReservationDTO> getUserReservationById(@PathVariable Long id) {
		User user =  userService.getCurrentUser();
		ReservationDTO reservationDTO =  reservationService.findByIdAndUser(id,user);
		return ResponseEntity.ok(reservationDTO);
	}
	
	//***********  Customer veya Admin kendine ait olan reservasyon bilgilerini tümünü  PAGEABLE olarak getirsin  **********************
		@GetMapping("/auth/all")
		@PreAuthorize( "hasRole('ADMIN') or hasRole('CUSTOMER')  " )
		public ResponseEntity<Page<ReservationDTO>> getAllUserReservations(
				
				@RequestParam("page") int page,
				@RequestParam("size") int size,
				@RequestParam("sort") String prop,//neye göre sıralanacağı belirtiliyor
				@RequestParam(value="direction",
										required = false, // direction required olmasın
										defaultValue = "DESC") Direction direction )  {
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
			
				User user = userService.getCurrentUser();
				
				Page<ReservationDTO> reservationDTOPage  = reservationService.findReservationPageByUser(user, pageable);
				
				return ResponseEntity.ok(reservationDTOPage);
			
		}
		
		//***************************** DELETE ***************************************
		
		@DeleteMapping("/admin/{id}/auth")
		@PreAuthorize("hasRole('ADMIN')")
		public ResponseEntity<VRResponse> deleteReservation(@PathVariable Long id) {
			
			reservationService.removeById(id);
			
			VRResponse response = new VRResponse(ResponseMessage.RESERVATION_DELETED_RESPONSE_MESSAGE, true);
			
			return ResponseEntity.ok(response);
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
