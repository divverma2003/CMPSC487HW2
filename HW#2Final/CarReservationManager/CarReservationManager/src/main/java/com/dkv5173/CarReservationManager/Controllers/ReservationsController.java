package com.dkv5173.CarReservationManager.Controllers;

import com.dkv5173.CarReservationManager.Models.Cars;
import com.dkv5173.CarReservationManager.Models.Reservations;
import com.dkv5173.CarReservationManager.Models.ReservationsDTO;
import com.dkv5173.CarReservationManager.Services.CarsRepository;
import com.dkv5173.CarReservationManager.Services.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/reservations")
public class ReservationsController {
    @Autowired
    private ReservationsRepository reservationsRepository;
    @Autowired
    private CarsRepository carsRepository;

    // Loads the reservation table with data
    @GetMapping({"", "/"})
    public String showReservations(Model model) {
        List<Reservations> reservations = reservationsRepository.findAll(Sort.by(Sort.Direction.DESC, "reservationEndDate"));
        model.addAttribute("reservations", reservations);
        return "/reservations/view";
    }

    // GET request to create a reservation
    @GetMapping("/create")
    public String showReservationForm(Model model) {
        ReservationsDTO reservationsDTO = new ReservationsDTO();
        List<Cars> cars = carsRepository.findAll();
        model.addAttribute("reservationsDTO", reservationsDTO);
        model.addAttribute("cars", cars); // To allow access to all the cars in the repo while booking
        return "/reservations/createReservation";
    }

    // GOT request tp create a reservation
    @PostMapping("/create")
    public String createReservation(@ModelAttribute("reservationsDTO") ReservationsDTO reservationsDTO, BindingResult bindingResult) {
        // If there's errors with the user's input, refresh the form --> don't submit
        if (bindingResult.hasErrors()) {
            return "/reservations/createReservation";
        }

        Date reservationStartDate = new Date();
        Date reservationEndDate = new Date();
        Cars car = carsRepository.findById(reservationsDTO.getCarId()).orElseThrow(() -> new IllegalArgumentException("Invalid car Id"));
        try{
            // Starting time is initialized to be the current time
            LocalDateTime startDate = LocalDateTime.now();
            reservationStartDate = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
            // User reservation length to calculate the ending date
            int reservationLength = reservationsDTO.getReservationLength();

            LocalDateTime endDate = startDate.plusDays(reservationLength);
            reservationEndDate = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        } catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }

        // Initialize and update inventory
        Reservations reservation = new Reservations();
        reservation.setReservationStartDate(reservationStartDate);
        reservation.setReservationEndDate(reservationEndDate);
        reservation.setDriverName(reservationsDTO.getDriverName());
        reservation.setCar(car);
        reservation.setReservationPrice(reservationsDTO.getReservationCost());
        reservationsRepository.save(reservation); // Save new entity onto repo
        return "redirect:/reservations";
    }
}