package com.busticket.entity;
import com.busticket.entity.enums.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    @NotNull
    private Trip trip;

    @Column(nullable = false)
    @NotNull
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private BookingStatus status;
}