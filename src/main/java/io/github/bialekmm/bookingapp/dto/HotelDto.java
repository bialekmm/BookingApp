package io.github.bialekmm.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Long id;
    private String name;
    private String description;
    private String country;
    private String city;
    private String street;
    private String streetNum;
    private String zipCode;
    private int stars;
    private List<RoomDto> rooms;
}
