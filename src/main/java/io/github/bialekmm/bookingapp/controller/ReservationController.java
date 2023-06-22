package io.github.bialekmm.bookingapp.controller;

import io.github.bialekmm.bookingapp.dto.HotelDto;
import io.github.bialekmm.bookingapp.dto.ReservationDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;
import io.github.bialekmm.bookingapp.dto.UserDto;
import io.github.bialekmm.bookingapp.entity.ReservationEntity;
import io.github.bialekmm.bookingapp.service.HotelService;
import io.github.bialekmm.bookingapp.service.ReservationService;
import io.github.bialekmm.bookingapp.service.RoomService;
import io.github.bialekmm.bookingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
public class ReservationController {
    private final HotelService hotelService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final UserService userService;

    @Autowired
    public ReservationController(HotelService hotelService, RoomService roomService, ReservationService reservationService, UserService userService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @GetMapping("/reservation/list")
    public String reservations( Model model){
        List<ReservationDto> reservations = reservationService.findAllReservations();
        model.addAttribute("reservations", reservations);
        return "reservationlist";
    }
    @GetMapping("/reservation/add")
    public String showReservationForm(@RequestParam(name = "hotelId", required = false) Long hotelId,Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long userId = userService.findByEmail(email).getId();
        String userName = userService.findByEmail(email).getFirstName() + " " + userService.findByEmail(email).getLastName();
        ReservationEntity reservationEntity = new ReservationEntity();
        List<HotelDto> hotels = hotelService.findAllHotels();
        List<RoomDto> rooms;
        if(hotelId != null){
            rooms = hotelService.findByHotelId(hotelId);
        }
        else {
            rooms = roomService.findAllRooms();
        }
        model.addAttribute("rooms", rooms);
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("hotels", hotels);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("reservation", reservationEntity);
        model.addAttribute("hotelId", hotelId);
        return "reservationadd";
    }
    @PostMapping("/reservation/add")
    public String addReservation(@ModelAttribute("reservation") ReservationDto reservationDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Long userId = userService.findByEmail(email).getId();
        reservationDto.setUserId(userId);
        reservationService.saveReservation(reservationDto);
        return "redirect:/reservation/add?success";
    }

    @GetMapping("/reservation/delete")
    public String deleteReservation(@RequestParam Long id){
        reservationService.deleteReservation(id);
        return "redirect:/reservation/list";
    }

}
