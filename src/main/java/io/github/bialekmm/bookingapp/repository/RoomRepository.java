package io.github.bialekmm.bookingapp.repository;

import io.github.bialekmm.bookingapp.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

}
