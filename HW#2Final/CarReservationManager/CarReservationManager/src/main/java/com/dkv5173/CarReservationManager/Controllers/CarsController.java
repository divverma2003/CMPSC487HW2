package com.dkv5173.CarReservationManager.Controllers;

import com.dkv5173.CarReservationManager.Models.Cars;
import com.dkv5173.CarReservationManager.Models.CarsDTO;
import com.dkv5173.CarReservationManager.Services.CarsRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarsController {

    // Dependency Injection
    @Autowired
    private CarsRepository carsRepository;

    // Loads the car inventory table with data
    @GetMapping({"", "/"})
    public String showCars(Model model) {
        List<Cars> cars = carsRepository.findAll(Sort.by(Sort.Direction.ASC, "carId"));
        model.addAttribute("cars", cars);
        return "/cars/view";
    }

    // GET request for creating a car
    @GetMapping("/create")
    public String addCar(Model model) {
        CarsDTO carsDTO = new CarsDTO();
        model.addAttribute("carsDTO", carsDTO);
        return "cars/createCar";
    }

    // POST request for creating a car
    @PostMapping("/create")
    public String createCar(@Valid @ModelAttribute("carsDTO") CarsDTO carsDTO, BindingResult bindingResult) {
        // If the user doesn't upload an image, the following error message appears
        if (carsDTO.getImage().isEmpty()) {
            bindingResult.addError(new FieldError("carsDTO", "image", "Image file is required."));
        }
        // If there are other errors, refresh the page --> don't submit
        if (bindingResult.hasErrors()) {
            return "/cars/createCar";
        }

        // Image variable initialization
        MultipartFile image = carsDTO.getImage();
        Date createDate = new Date();
        // Ensures unique naming
        String imageFileName = createDate.getTime() + "_" + image.getOriginalFilename();
        try {
            // Directory where images will be stored
            String directory = "public/CarImages/";
            Path uploadPath = Paths.get(directory);

            // Create directories if they do not exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the uploaded image
            try (InputStream inputStream = image.getInputStream()) {
                Path imagePath = uploadPath.resolve(imageFileName);
                Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        // Initialize new car entity
        Cars car = new Cars();
        car.setVin(carsDTO.getVin());
        car.setMake(carsDTO.getMake());
        car.setModel(carsDTO.getModel());
        car.setTypeOfCar(carsDTO.getTypeOfCar());
        car.setYearCreated(carsDTO.getYearCreated());
        car.setImageName(imageFileName);
        car.setColor(carsDTO.getColor());
        car.setCarStatus("Available");
        car.setPricePerDay(carsDTO.getPricePerDay());

        carsRepository.save(car); // Add to repo

        return "redirect:/cars";
    }

    @GetMapping("/edit")
    public String findEditPage(Model model, @RequestParam Long carId) {
        try {
            Cars car = carsRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + carId));
            model.addAttribute("car", car); // Add the existing car object to the model

            CarsDTO carsDTO = new CarsDTO();

            // Transfer values from car to carsDTO
            carsDTO.setVin(car.getVin());
            carsDTO.setMake(car.getMake());
            carsDTO.setModel(car.getModel());
            carsDTO.setTypeOfCar(car.getTypeOfCar());
            carsDTO.setYearCreated(car.getYearCreated());
            carsDTO.setColor(car.getColor());
            carsDTO.setPricePerDay(car.getPricePerDay());

            model.addAttribute("carsDTO", carsDTO);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/cars";
        }
        return "cars/editCar";
    }


    // GET request for editing a car
    @PostMapping("/edit/{carId}")
    public String updateCar(
            @PathVariable("carId") Long carId,
            @Valid @ModelAttribute("carsDTO") CarsDTO carsDTO,
            BindingResult bindingResult,
            Model model) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Retrieve the existing car and add it back to the model
            Cars car = carsRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + carId));
            model.addAttribute("car", car);  // To retain readonly fields, prevents null object
            return "cars/editCar";  // Return the form with errors
        }

        // Pulls the relevant car from the repo
        Cars car = carsRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + carId));

        // If the user decided the update the image
        if (!carsDTO.getImage().isEmpty()) {
            String directory = "public/CarImages/";
            Path oldImagePath = Paths.get(directory + car.getImageName());
            try {
                Files.delete(oldImagePath); // Delete old image
            }
            catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            // Generate new image path and svae the image
            MultipartFile image = carsDTO.getImage();
            String imageFileName = new Date().getTime() + "_" + image.getOriginalFilename();

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(directory + imageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            car.setImageName(imageFileName);

        }
        // If no errors, process the update
        car.setMake(carsDTO.getMake());
        car.setModel(carsDTO.getModel());
        car.setYearCreated(carsDTO.getYearCreated());
        car.setTypeOfCar(carsDTO.getTypeOfCar());
        car.setColor(carsDTO.getColor());
        car.setPricePerDay(carsDTO.getPricePerDay());

        // Save the updated car entity
        carsRepository.save(car);

        return "redirect:/cars";
    }

    // GET request for deleting a car
    @GetMapping("/delete")
    public String deleteCar(Model model, @RequestParam Long carId) {
        try {
            Cars car = carsRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + carId));
            Path imagePath = Paths.get("public/CarImages/"  + car.getImageName());
            try {
                // Deletes image
                Files.delete(imagePath);
            } catch (Exception e) {System.out.println("Exception: " + e.getMessage());}

            // Deletes entity
            carsRepository.delete(car);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/cars";
    }
}
