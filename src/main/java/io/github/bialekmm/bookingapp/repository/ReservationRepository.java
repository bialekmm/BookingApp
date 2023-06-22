package io.github.bialekmm.bookingapp.repository;

import io.github.bialekmm.bookingapp.entity.ReservationEntity;
import io.github.bialekmm.bookingapp.entity.RoomEntity;
import io.github.bialekmm.bookingapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    ReservationEntity findByUser(UserEntity user);
    List<ReservationEntity> findByRoom(RoomEntity room);
}
