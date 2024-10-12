package com.dkv5173.CarReservationManager.Models;


import jakarta.persistence.*;
import java.util.Date;

// Corresponding MySQL table
@Entity
@Table(name = "reservation")
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Member variables
    private Long reservationsId;

    // Reservations have a foreign key referencing the car that will be reserved
    @ManyToOne
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "fk_car_reservation", foreignKeyDefinition = "FOREIGN KEY (car_id) REFERENCES car(car_id) ON DELETE CASCADE"))
    private Cars car;
    private String driverName;
    private Date reservationStartDate;
    private Date reservationEndDate;
    private double reservationPrice;

    // Getters & Setters
    public Long getReservationsId() {
        return reservationsId;
    }

    public void setReservationsId(Long reservations_id) {
        this.reservationsId = reservations_id;
    }

    public Cars getCar() {
        return car;
    }

    public void setCar(Cars car) {
        this.car = car;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getReservationStartDate() {
        return reservationStartDate;
    }

    public void setReservationStartDate(Date reservationStartDate) {
        this.reservationStartDate = reservationStartDate;
    }

    public Date getReservationEndDate() {
        return reservationEndDate;
    }

    public void setReservationEndDate(Date reservationEndDate) {
        this.reservationEndDate = reservationEndDate;
    }

    public double getReservationPrice() {
        return reservationPrice;
    }

    public void setReservationPrice(double reservationPrice) {
        this.reservationPrice = reservationPrice;
    }
}

