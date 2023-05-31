package io.github.bialekmm.bookingapp.controller;

import io.github.bialekmm.bookingapp.dto.HotelDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;
import io.github.bialekmm.bookingapp.dto.UserDto;
import io.github.bialekmm.bookingapp.entity.HotelEntity;
import io.github.bialekmm.bookingapp.entity.RoomEntity;
import io.github.bialekmm.bookingapp.service.HotelService;
import io.github.bialekmm.bookingapp.service.RoomService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HotelController {
    private final HotelService hotelService;
    private final RoomService roomService;

    public HotelController(HotelService hotelService, RoomService roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @GetMapping("/hotel/list")
    public String users(Model model){
        List<HotelDto> hotels = hotelService.findAllHotels();
        List<RoomDto> rooms = roomService.findAllRooms();
        model.addAttribute("hotels", hotels);
        model.addAttribute("rooms", rooms);
        return "hotellist";
    }
    @GetMapping("/hotel/add")
    public String showHotelForm(Model model){
        // create model object to store form data
        HotelEntity hotel = new HotelEntity();
        model.addAttribute("hotel", hotel);
        return "hoteladd";
    }
    @PostMapping("/hotel/add")
    public String addHotel(@ModelAttribute("hotel") HotelDto hotelDto){
        hotelService.saveHotel(hotelDto);
        return "redirect:/hotel/add?success";
    }

    @GetMapping("/room/add")
    public String showRoomForm(Model model){
        // create model object to store form data
        RoomEntity room = new RoomEntity();
        model.addAttribute("room", room);
        return "hoteladd";
    }
}
