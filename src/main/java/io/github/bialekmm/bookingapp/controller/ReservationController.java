package io.github.bialekmm.bookingapp.controller;

import io.github.bialekmm.bookingapp.dto.HotelDto;
import io.github.bialekmm.bookingapp.dto.ReservationDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;
import io.github.bialekmm.bookingapp.entity.HotelEntity;
import io.github.bialekmm.bookingapp.entity.ReservationEntity;
import io.github.bialekmm.bookingapp.service.HotelService;
import io.github.bialekmm.bookingapp.service.ReservationService;
import io.github.bialekmm.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ReservationController {
    private final HotelService hotelService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    public ReservationController(HotelService hotelService, RoomService roomService, ReservationService reservationService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    @GetMapping("/reservation/list")
    public String reservations(Model model){
        List<ReservationDto> reservations = reservationService.findAllReservations();
        model.addAttribute("reservations", reservations);
        return "reservationlist";
    }
    @GetMapping("/reservation/add")
    public String showReservationForm(Model model){
        ReservationEntity reservationEntity = new ReservationEntity();
        model.addAttribute("reservation", reservationEntity);
        return "reservationadd";
    }
    @PostMapping("/reservation/add")
    public String addReservation(@ModelAttribute("reservation") ReservationDto reservationDto){
        reservationService.saveReservation(reservationDto);
        return "redirect:/reservation/add?success";
    }

    @GetMapping("/reservation/delete")
    public String deleteReservation(@RequestParam Long id){
        reservationService.deleteReservation(id);
        return "redirect:/reservation/list";
    }
}
