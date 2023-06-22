package io.github.bialekmm.bookingapp.service;

import io.github.bialekmm.bookingapp.dto.ReservationDto;
import io.github.bialekmm.bookingapp.entity.RoomEntity;
import io.github.bialekmm.bookingapp.entity.UserEntity;

import java.util.List;

public interface ReservationService {
    List<ReservationDto> findAllReservations();
    ReservationDto findByUser(UserEntity user);
    List<ReservationDto> findByRoom(RoomEntity room);
    void saveReservation(ReservationDto reservationDto);
    void deleteReservation(Long id);
}
