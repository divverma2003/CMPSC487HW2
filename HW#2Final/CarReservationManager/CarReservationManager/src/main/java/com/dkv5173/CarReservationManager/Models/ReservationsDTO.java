package com.dkv5173.CarReservationManager.Models;
import jakarta.validation.constraints.*;

// Class to store user input data, before the object is initialized as a "Reservation"
public class ReservationsDTO {
    @NotNull(message = "This field is required.")
    private Long carId;

    @NotEmpty(message = "This field is required.")
    private String driverName;

    @NotNull(message = "This field is required.")
    @Min(value = 1, message = "Reservation must be at-least one day long." )
    private Integer reservationLength;

    @NotNull(message = "This field is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Reservation cost must be greater than zero.")
    private Double reservationCost;

    // Setters & Getters
    public @NotNull(message = "This field is required.") @DecimalMin(value = "0.0", inclusive = false, message = "Reservation cost must be greater than zero.") Double getReservationCost() {
        return reservationCost;
    }

    public void setReservationCost(@NotNull(message = "This field is required.") @DecimalMin(value = "0.0", inclusive = false, message = "Reservation cost must be greater than zero.") Double reservationCost) {
        this.reservationCost = reservationCost;
    }

    public @NotNull(message = "This field is required.") Long getCarId() {
        return carId;
    }

    public void setCarId(@NotNull(message = "This field is required.") Long carId) {
        this.carId = carId;
    }

    public @NotEmpty(message = "This field is required.") String getDriverName() {
        return driverName;
    }

    public void setDriverName(@NotEmpty(message = "This field is required.") String driverName) {
        this.driverName = driverName;
    }

    public @Min(value = 1, message = "Reservation must be at-least one day long.") Integer getReservationLength() {
        return reservationLength;
    }

    public void setReservationLength(@Min(value = 1, message = "Reservation must be at-least one day long.") Integer reservationLength) {
        this.reservationLength = reservationLength;
    }
}
