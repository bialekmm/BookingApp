package io.github.bialekmm.bookingapp.service;

import io.github.bialekmm.bookingapp.dto.HotelDto;

import java.util.List;

public interface HotelService {
    List<HotelDto> findAllHotels();

    void saveHotel(HotelDto hotelDto);

    void deleteHotel(Long id);
}
