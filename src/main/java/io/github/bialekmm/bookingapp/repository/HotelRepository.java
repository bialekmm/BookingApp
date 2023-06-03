package io.github.bialekmm.bookingapp.repository;

import io.github.bialekmm.bookingapp.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelRepository extends JpaRepository<HotelEntity, Long> {
}
