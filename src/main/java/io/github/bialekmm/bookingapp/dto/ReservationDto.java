package io.github.bialekmm.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guests;
    private String status;
    private Long userId;
    private Long roomId;
}
