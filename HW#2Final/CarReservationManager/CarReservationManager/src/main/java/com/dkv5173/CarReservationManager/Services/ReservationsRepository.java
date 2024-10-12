package com.dkv5173.CarReservationManager.Services;

import com.dkv5173.CarReservationManager.Models.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationsRepository extends JpaRepository<Reservations, Long> {
}