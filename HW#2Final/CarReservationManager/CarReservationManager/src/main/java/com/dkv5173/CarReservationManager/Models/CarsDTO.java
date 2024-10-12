package com.dkv5173.CarReservationManager.Models;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

// Class to store user input data, before the object is initialized as a "car"
public class CarsDTO {

    // Constraints to ensure that the user input field isn't empty
    @NotEmpty(message = "This field is required.")
    @Size(min = 17, max = 17, message = "You must input a VIN of size 17.")
    private String vin;

    @NotEmpty(message = "This field is required.")
    private String make;

    @NotEmpty(message = "This field is required.")
    private String model;

    @NotNull(message = "This field is required.")
    @Min(value = 1950, message = "Year must be greater than or equal to 1950.")
    @Max(value = 2025, message = "Year must be less than or equal to 2025.")
    private Integer yearCreated;

    @NotEmpty(message = "This field is required.")
    private String typeOfCar;

    @NotEmpty(message = "This field is required.")
    private String color;

    @NotNull(message = "This field is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be greater than zero.")
    private Double pricePerDay;

    @NotNull(message = "This field is required.")
    private MultipartFile image;

    // Getters & Setters
    public @NotEmpty(message = "This field is required.") @Size(min = 17, max = 17, message = "You must input a VIN of size 17.") String getVin() {
        return vin;
    }

    public void setVin(@NotEmpty(message = "This field is required.") @Size(min = 17, max = 17, message = "You must input a VIN of size 17.") String vin) {
        this.vin = vin;
    }

    public @NotEmpty(message = "This field is required.") String getMake() {
        return make;
    }

    public void setMake(@NotEmpty(message = "This field is required.") String make) {
        this.make = make;
    }

    public @NotEmpty(message = "This field is required.") String getModel() {
        return model;
    }

    public void setModel(@NotEmpty(message = "This field is required.") String model) {
        this.model = model;
    }

    public @NotNull(message = "This field is required.") @Min(value = 1950, message = "Year must be greater than or equal to 1950.") @Max(value = 2025, message = "Year must be less than or equal to 2025.") Integer getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(@NotNull(message = "This field is required.") @Min(value = 1950, message = "Year must be greater than or equal to 1950.") @Max(value = 2025, message = "Year must be less than or equal to 2025.") Integer yearCreated) {
        this.yearCreated = yearCreated;
    }

    public @NotEmpty(message = "This field is required.") String getTypeOfCar() {
        return typeOfCar;
    }

    public void setTypeOfCar(@NotEmpty(message = "This field is required.") String typeOfCar) {
        this.typeOfCar = typeOfCar;
    }

    public @NotEmpty(message = "This field is required.") String getColor() {
        return color;
    }

    public void setColor(@NotEmpty(message = "This field is required.") String color) {
        this.color = color;
    }

    public @NotNull(message = "This field is required.") @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be greater than zero.") Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(@NotNull(message = "This field is required.") @DecimalMin(value = "0.0", inclusive = false, message = "Price per day must be greater than zero.") Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public @NotNull(message = "This field is required.")  MultipartFile getImage() {
        return image;
    }

    public void setImage(@NotNull(message = "This field is required.") MultipartFile image) {
        this.image = image;
    }
}
