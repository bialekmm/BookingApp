package io.github.bialekmm.bookingapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reservation")
public class ReservationEntity {
    @Id
    @GeneratedValue(generator="inc")
    @GenericGenerator(name="inc", strategy = "increment")
    @Column(nullable = false)
    private Long id;

    @Column(name = "startdate", nullable = false)
    private LocalDate startDate;
    @Column(name = "enddate", nullable = false)
    private LocalDate endDate;
    @Column(name = "guests", nullable = false)
    private int guests;
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;
}
