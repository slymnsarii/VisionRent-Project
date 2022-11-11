package com.visionrent.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.visionrent.repository.ReservationRepository;
@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository ;
}