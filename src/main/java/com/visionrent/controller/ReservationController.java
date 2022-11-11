package com.visionrent.controller;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.visionrent.dto.request.ReservationRequest;
import com.visionrent.dto.response.VRResponse;
import com.visionrent.service.ReservationService;
@RestController
@RequestMapping("/reservations")
public class ReservationController {
	
	@Autowired
	private ReservationService reservationService;
	
	//******* Make Reservation ************************************
	
	/** TODO devamı yazılacak
	@PostMapping("/add")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('CUSTOMER')  " )
	public ResponseEntity<VRResponse> makeReservation(@RequestParam("carId") Long carId,
												  @Valid @RequestBody ReservationRequest reservationRequest) {
		
	}
	*/
	
}