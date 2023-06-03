package io.github.bialekmm.bookingapp.service;

import io.github.bialekmm.bookingapp.dto.HotelDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;

import java.util.List;

public interface HotelService {
    List<HotelDto> findAllHotels();
    void addRoom(RoomDto roomDto, HotelDto hotelDto);
    void saveHotel(HotelDto hotelDto);

    void deleteHotel(Long id);
}
