package io.github.bialekmm.bookingapp.service;

import io.github.bialekmm.bookingapp.dto.HotelDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;

import java.util.List;

public interface HotelService {
    List<HotelDto> findAllHotels();
    HotelDto findById(Long id);
    List<RoomDto> findByHotelId(Long id);
    void addRoom(RoomDto roomDto, HotelDto hotelDto);
    void saveHotel(HotelDto hotelDto);
    void deleteHotel(Long id);
}
