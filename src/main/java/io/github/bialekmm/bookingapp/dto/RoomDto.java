package io.github.bialekmm.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RoomDto {
    private Long id;
    private String name;
    private String description;
    private int guests;
    private Long hotelId;
}
