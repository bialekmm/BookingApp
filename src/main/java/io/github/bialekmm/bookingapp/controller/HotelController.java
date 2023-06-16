package io.github.bialekmm.bookingapp.controller;

import io.github.bialekmm.bookingapp.dto.HotelDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;
import io.github.bialekmm.bookingapp.entity.HotelEntity;
import io.github.bialekmm.bookingapp.service.HotelService;
import io.github.bialekmm.bookingapp.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Set;
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
    public String hotels(Model model){
        List<HotelDto> hotels = hotelService.findAllHotels();
        List<RoomDto> rooms = roomService.findAllRooms();
        List<String> roomNames = rooms.stream()
                .map(RoomDto::getName)
                .toList();
        model.addAttribute("roomNames", roomNames);
        model.addAttribute("hotels", hotels);
        model.addAttribute("rooms", rooms);
        return "hotellist";
    }
    @GetMapping("/hotel/add")
    public String showHotelForm(Model model){
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
        RoomDto room = new RoomDto();
        List<HotelDto> hotels = hotelService.findAllHotels();
        List<RoomDto> rooms = roomService.findAllRooms();
        Set<String> uniqueRoomNames = rooms.stream()
                .map(RoomDto::getName)
                .collect(Collectors.toSet());

        List<RoomDto> uniqueRooms = uniqueRoomNames.stream()
                        .map(name -> rooms.stream()
                                .filter(roomDto -> roomDto != null && roomDto.getName().equals(name))
                                .findFirst()
                                        .orElse(null))
                                .filter(Objects::nonNull)
                                        .toList();

        model.addAttribute("rooms", uniqueRooms);
        model.addAttribute("hotels", hotels);
        model.addAttribute("room", room);
        return "roomadd";
    }
    @PostMapping("/room/add")
    public String addRoom(@ModelAttribute("room") RoomDto roomDto, @RequestParam("roomId") String roomId, @RequestParam("hotelId") String hotelId){
        if(roomId.equals("create_new")){
            roomService.saveRoom(roomDto);
        }
        else
            roomService.assignRoomToHotel(roomId, hotelId);
        return "redirect:/room/add?success";
    }
    @GetMapping("/hotel/delete")
    public String deleteHotel(@RequestParam Long id){
        hotelService.deleteHotel(id);
        return "redirect:/hotel/list";
    }
    @GetMapping("/room/delete")
    public String deleteRoom(@RequestParam Long id){
        roomService.deleteRoom(id);
        return "redirect:/hotel/list";
    }
}
