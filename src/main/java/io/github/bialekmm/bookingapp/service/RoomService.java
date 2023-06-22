package io.github.bialekmm.bookingapp.service;

import io.github.bialekmm.bookingapp.dto.RoomDto;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    List<RoomDto> findAllRooms();
    void saveRoom(RoomDto roomDto);
    void deleteRoom(Long id);
    void assignRoomToHotel(String roomId, String hotelId);
    List<RoomDto> findAvailableRooms(Long hotelId, LocalDate startDate, LocalDate endDate, int numberOfGuests);
    boolean isRoomAvailable(Long roomId, LocalDate startDate, LocalDate endDate);
    boolean isOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2);
}
