package com.dkv5173.CarReservationManager.Controllers;

import com.dkv5173.CarReservationManager.Models.Cars;
import com.dkv5173.CarReservationManager.Services.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private CarsRepository carsRepository;
    @GetMapping("/")

    // GET function to fetch car entities and display them on the home page
    public String showCars(Model model) {
        List<Cars> cars = carsRepository.findAll(Sort.by(Sort.Direction.ASC, "carId"));
        model.addAttribute("cars", cars);
        return "/index";
    }
}
