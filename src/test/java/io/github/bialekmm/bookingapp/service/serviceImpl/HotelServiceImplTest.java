package io.github.bialekmm.bookingapp.service.serviceImpl;

import io.github.bialekmm.bookingapp.dto.HotelDto;
import io.github.bialekmm.bookingapp.dto.RoomDto;
import io.github.bialekmm.bookingapp.entity.HotelEntity;
import io.github.bialekmm.bookingapp.entity.RoomEntity;
import io.github.bialekmm.bookingapp.repository.HotelRepository;
import io.github.bialekmm.bookingapp.repository.ReservationRepository;
import io.github.bialekmm.bookingapp.repository.RoomRepository;
import io.github.bialekmm.bookingapp.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllHotels() {
        List<HotelEntity> hotelEntities = new ArrayList<>();
        HotelEntity hotel1 = new HotelEntity();
        hotel1.setRooms(new ArrayList<>());
        hotelEntities.add(hotel1);

        when(hotelRepository.findAll()).thenReturn(hotelEntities);

        List<HotelDto> hotelDtos = hotelService.findAllHotels();

        assertEquals(hotelEntities.size(), hotelDtos.size());

        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    public void testFindHotelByIdWhenExists() {
        Long hotelId = 1L;
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setId(hotelId);
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotelEntity));

        HotelDto hotelDto = hotelService.findById(hotelId);

        assertNotNull(hotelDto);
        assertEquals(hotelId, hotelDto.getId());

        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    public void testFindHotelByIdWhenNotExists() {
        Long hotelId = 1L;
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        HotelDto hotelDto = hotelService.findById(hotelId);

        assertNull(hotelDto);

        verify(hotelRepository, times(1)).findById(hotelId);
    }

    @Test
    public void testFindHotelByRoomId() {
        HotelEntity mockHotel = new HotelEntity();
        mockHotel.setId(1L);
        mockHotel.setName("Mock Hotel");

        RoomEntity mockRoom = new RoomEntity();
        mockRoom.setId(1L);
        mockRoom.setName("Mock Room");
        mockRoom.setHotel(mockHotel);

        when(hotelRepository.findByRoomsId(1L)).thenReturn(mockHotel);

        HotelDto resultHotelDto = hotelService.findHotelByRoomId(1L);

        assertEquals(mockHotel.getName(), resultHotelDto.getName());
    }

    @Test
    public void testFindByHotelId_ValidHotelId() {
        // Mock hotel entity and room entities
        HotelEntity mockHotel = new HotelEntity();
        mockHotel.setId(1L);
        mockHotel.setName("Test Hotel");

        RoomEntity room1 = new RoomEntity();
        room1.setId(101L);
        room1.setName("Room 101");
        room1.setHotel(mockHotel);

        RoomEntity room2 = new RoomEntity();
        room2.setId(102L);
        room2.setName("Room 102");
        room2.setHotel(mockHotel);

        mockHotel.setRooms(Arrays.asList(room1, room2));

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(mockHotel));


        List<RoomDto> roomDtoList = hotelService.findByHotelId(1L);

        assertEquals(2, roomDtoList.size());
        assertEquals("Room 101", roomDtoList.get(0).getName());
        assertEquals("Room 102", roomDtoList.get(1).getName());
    }

    @Test
    public void testFindRoomById() {
        Long roomId = 1L;

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(roomId);

        when(roomRepository.findById(roomId)).thenReturn(Optional.of(roomEntity));

        RoomDto result = roomService.findRoomById(roomId);

        assertNotNull(result);
        assertEquals(roomEntity.getId(), result.getId());
    }
}