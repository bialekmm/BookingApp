package io.github.bialekmm.bookingapp.service.serviceImpl;

import io.github.bialekmm.bookingapp.dto.ReservationDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;
import io.github.bialekmm.bookingapp.entity.ReservationEntity;
import io.github.bialekmm.bookingapp.entity.RoomEntity;
import io.github.bialekmm.bookingapp.entity.UserEntity;
import io.github.bialekmm.bookingapp.repository.ReservationRepository;
import io.github.bialekmm.bookingapp.repository.RoomRepository;
import io.github.bialekmm.bookingapp.repository.UserRepository;
import io.github.bialekmm.bookingapp.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, RoomRepository roomRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }
    private ReservationDto mapToReservationDto(ReservationEntity reservationEntity){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setRoomId(reservationEntity.getRoom().getId());
        reservationDto.setUserId(reservationEntity.getUser().getId());
        reservationDto.setGuests(reservationEntity.getGuests());
        reservationDto.setStatus(reservationEntity.getStatus());
        reservationDto.setId(reservationEntity.getId());
        reservationDto.setStartDate(reservationEntity.getStartDate());
        reservationDto.setEndDate(reservationEntity.getEndDate());
        return reservationDto;
    }

    @Override
    public List<ReservationDto> findAllReservations() {
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        return reservationEntities.stream().
                map(this::mapToReservationDto).
                toList();
    }


    @Override
    public List<ReservationDto> findByUserEmail(String userEmail) {
        List<ReservationEntity> reservationEntities = reservationRepository.findByUserEmail(userEmail);
        return reservationEntities.stream().
                map(this::mapToReservationDto).
                toList();
    }


    @Override
    public List<ReservationDto> findByRoom(RoomEntity room) {
        List<ReservationEntity> reservationEntities = reservationRepository.findByRoom(room);
        return reservationEntities.stream().
                map(this::mapToReservationDto).
                toList();
    }

    @Override
    public void saveReservation(ReservationDto reservationDto) {
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(reservationDto.getId());
        reservationEntity.setStatus(reservationDto.getStatus());
        reservationEntity.setStartDate(reservationDto.getStartDate());
        reservationEntity.setEndDate(reservationDto.getEndDate());
        reservationEntity.setGuests(reservationDto.getGuests());
        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(reservationDto.getRoomId());
        if (roomEntityOptional.isPresent()){
            RoomEntity roomEntity = roomEntityOptional.get();
            reservationEntity.setRoom(roomEntity);
        }
        Optional<UserEntity> userEntityOptional = userRepository.findById(reservationDto.getUserId());
        if (userEntityOptional.isPresent()){
            UserEntity userEntity = userEntityOptional.get();
            reservationEntity.setUser(userEntity);
        }
        reservationRepository.save(reservationEntity);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public void changeReservationStatus(Long reservationId, String newStatus) {
        Optional<ReservationEntity> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            ReservationEntity reservation = reservationOptional.get();
            reservation.setStatus(newStatus);
            reservationRepository.save(reservation);
        } else {
            throw new IllegalArgumentException("Reservation with ID " + reservationId + " not found");
        }
    }
}


