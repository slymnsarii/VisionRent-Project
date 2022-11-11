package com.visionrent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visionrent.domain.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}
