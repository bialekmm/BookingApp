package io.github.bialekmm.bookingapp.service.serviceImpl;

import io.github.bialekmm.bookingapp.dto.HotelDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;
import io.github.bialekmm.bookingapp.entity.HotelEntity;
import io.github.bialekmm.bookingapp.entity.ReservationEntity;
import io.github.bialekmm.bookingapp.entity.RoomEntity;
import io.github.bialekmm.bookingapp.repository.HotelRepository;
import io.github.bialekmm.bookingapp.repository.ReservationRepository;
import io.github.bialekmm.bookingapp.repository.RoomRepository;
import io.github.bialekmm.bookingapp.service.HotelService;
import io.github.bialekmm.bookingapp.service.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService, RoomService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }
    private HotelDto mapToHotelDto(HotelEntity hotelEntity) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(hotelEntity.getId());
        hotelDto.setName(hotelEntity.getName());
        hotelDto.setDescription(hotelEntity.getDescription());
        hotelDto.setCountry(hotelEntity.getCountry());
        hotelDto.setCity(hotelEntity.getCity());
        hotelDto.setStreet(hotelEntity.getStreet());
        hotelDto.setStreetNum(hotelEntity.getStreetNum());
        hotelDto.setZipCode(hotelEntity.getZipCode());
        hotelDto.setStars(hotelEntity.getStars());
        List<RoomDto> roomDtoList = hotelEntity.getRooms().stream().
                map(this::mapToRoomDto).
                toList();
        hotelDto.setRooms(roomDtoList);
        return hotelDto;
    }
    private RoomDto mapToRoomDto(RoomEntity roomEntity){
        RoomDto roomDto = new RoomDto();
        roomDto.setId(roomEntity.getId());
        roomDto.setName(roomEntity.getName());
        roomDto.setDescription(roomEntity.getDescription());
        roomDto.setGuests(roomEntity.getGuests());
        roomDto.setHotelId(roomEntity.getHotel().getId());
        return roomDto;
    }
    private RoomEntity mapToRoomEntity(RoomDto roomDto){
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomDto.getId());
        roomEntity.setName(roomDto.getName());
        roomEntity.setDescription(roomDto.getDescription());
        roomEntity.setGuests(roomDto.getGuests());
        Optional<HotelEntity> hotelEntityOptional = hotelRepository.findById(roomDto.getHotelId());
        if (hotelEntityOptional.isPresent()) {
            HotelEntity hotelEntity = hotelEntityOptional.get();
            roomEntity.setHotel(hotelEntity);
        }
        return roomEntity;
    }
    @Override
    public List<HotelDto> findAllHotels() {
        List<HotelEntity> hotelEntities = hotelRepository.findAll();
        return hotelEntities.stream().
                map(this::mapToHotelDto).
                toList();
    }

    @Override
    public HotelDto findById(Long id) {
        Optional<HotelEntity> hotelEntityOptional = hotelRepository.findById(id);
        if(hotelEntityOptional.isPresent()){
            HotelEntity hotelEntity = hotelEntityOptional.get();
            return mapToHotelDto(hotelEntity);
        }
       return null;
    }

    @Override
    public HotelDto findHotelByRoomId(Long roomId) {
        return mapToHotelDto(hotelRepository.findByRoomsId(roomId));
    }

    @Override
    public List<RoomDto> findByHotelId(Long id) {
        Optional<HotelEntity> hotelEntityOptional = hotelRepository.findById(id);
        if(hotelEntityOptional.isPresent()){
            HotelEntity hotelEntity = hotelEntityOptional.get();
            List<RoomEntity> roomEntityList = hotelEntity.getRooms();
            return roomEntityList.stream().
                    map(this::mapToRoomDto).
                    toList();
        }
        else {
            System.out.println("Room or hotel not found");
            return Collections.emptyList();
        }
    }

    @Override
    public void addRoom(RoomDto roomDto, HotelDto hotelDto) {
        Long roomId = roomDto.getId();
        Long hotelId = hotelDto.getId();

        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(roomId);
        Optional<HotelEntity> hotelEntityOptional = hotelRepository.findById(hotelId);

        if (roomEntityOptional.isPresent() && hotelEntityOptional.isPresent()) {
            RoomEntity roomEntity = new RoomEntity();
            HotelEntity hotelEntity = hotelEntityOptional.get();

            roomEntity.setName(roomEntityOptional.get().getName());
            roomEntity.setGuests(roomEntityOptional.get().getGuests());
            roomEntity.setDescription(roomEntityOptional.get().getDescription());
            roomEntity.setHotel(hotelEntity);
            hotelEntity.getRooms().add(roomEntity);

            roomRepository.save(roomEntity);
            hotelRepository.save(hotelEntity);
        }
    }

    @Override
    public List<RoomDto> findAllRooms() {
        List<RoomEntity> roomEntityList = roomRepository.findAll();
        return roomEntityList.stream().
                map(this::mapToRoomDto).
                toList();
    }

    @Override
    public RoomDto findRoomById(Long id) {
        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(id);
        if(roomEntityOptional.isPresent()){
            RoomEntity roomEntity = roomEntityOptional.get();
            return mapToRoomDto(roomEntity);
        }
        return null;
    }

    @Override
    public void saveHotel(HotelDto hotelDto) {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setId(hotelDto.getId());
        hotelEntity.setName(hotelDto.getName());
        hotelEntity.setDescription(hotelDto.getDescription());
        hotelEntity.setCountry(hotelDto.getCountry());
        hotelEntity.setCity(hotelDto.getCity());
        hotelEntity.setStreet(hotelDto.getStreet());
        hotelEntity.setStreetNum(hotelDto.getStreetNum());
        hotelEntity.setZipCode(hotelDto.getZipCode());
        hotelEntity.setStars(hotelDto.getStars());
        if(hotelDto.getRooms() != null){
            List<RoomEntity> roomEntityList = hotelDto.getRooms().stream().
                    map(this::mapToRoomEntity).
                    toList();
                    hotelEntity.setRooms(roomEntityList);
        }
        hotelRepository.save(hotelEntity);
    }
    @Override
    public void saveRoom(RoomDto roomDto) {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomDto.getId());
        roomEntity.setName(roomDto.getName());
        roomEntity.setDescription(roomDto.getDescription());
        roomEntity.setGuests(roomDto.getGuests());
        Optional<HotelEntity> hotelEntityOptional = hotelRepository.findById(roomDto.getHotelId());
        if (hotelEntityOptional.isPresent()) {
            HotelEntity hotelEntity = hotelEntityOptional.get();
            roomEntity.setHotel(hotelEntity);
        }
        roomRepository.save(roomEntity);
    }
    @Override
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
    @Override
    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public void assignRoomToHotel(String roomId, String hotelId) {
        Long roomIdLong = Long.valueOf(roomId);
        Long hotelIdLong = Long.valueOf(hotelId);
        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(roomIdLong);
        Optional<HotelEntity> hotelEntityOptional = hotelRepository.findById(hotelIdLong);
        if(roomEntityOptional.isPresent() && hotelEntityOptional.isPresent()){
            RoomEntity roomEntity = roomEntityOptional.get();
            HotelEntity hotelEntity = hotelEntityOptional.get();

            RoomDto roomDto = mapToRoomDto(roomEntity);
            HotelDto hotelDto = mapToHotelDto(hotelEntity);

            addRoom(roomDto, hotelDto);
        }
        else {
            System.out.println("Room or hotel not found");
        }
    }

    @Override
    public List<RoomDto> findAvailableRooms(Long hotelId, LocalDate startDate, LocalDate endDate, int numberOfGuests) {
        List<RoomDto> availableRooms = new ArrayList<>();

        List<RoomDto> rooms = findByHotelId(hotelId);

        for (RoomDto room : rooms) {
            if (isRoomAvailable(room.getId(), startDate, endDate) && room.getGuests() >= numberOfGuests) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    @Override
    public boolean isRoomAvailable(Long roomId, LocalDate startDate, LocalDate endDate) {
        Optional<RoomEntity> roomEntityOptional = roomRepository.findById(roomId);
        if(roomEntityOptional.isPresent()){
            RoomEntity roomEntity = roomEntityOptional.get();
            List<ReservationEntity> reservations = reservationRepository.findByRoom(roomEntity);
            for (ReservationEntity reservation : reservations) {
                if (isOverlapping(reservation.getStartDate(), reservation.getEndDate(), startDate, endDate)) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public boolean isOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
}
