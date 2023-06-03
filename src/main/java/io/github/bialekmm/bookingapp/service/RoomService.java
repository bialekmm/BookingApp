package io.github.bialekmm.bookingapp.service;

import io.github.bialekmm.bookingapp.dto.RoomDto;

import java.util.List;

public interface RoomService {
    List<RoomDto> findAllRooms();
    void saveRoom(RoomDto roomDto);
    void deleteRoom(Long id);
    void assignRoomToHotel(String roomId, String hotelId);
}
