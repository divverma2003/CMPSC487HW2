package com.dkv5173.CarReservationManager.Services;

import com.dkv5173.CarReservationManager.Models.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Cars, Long> {
}