package com.busticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Integer tripId;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    @NotNull
    private Route route;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    @NotNull
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "boarding_address_id", nullable = false)
    @NotNull
    private Address boardingAddress;

    @ManyToOne
    @JoinColumn(name = "dropping_address_id", nullable = false)
    @NotNull
    private Address droppingAddress;

    @Column(name = "departure_time", nullable = false)
    @NotNull
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    @NotNull
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "driver1_driver_id", nullable = false)
    @NotNull
    private Driver driver1;

    @ManyToOne
    @JoinColumn(name = "driver2_driver_id", nullable = false)
    @NotNull
    private Driver driver2;

    @Column(name = "available_seats", nullable = false)
    @NotNull
    private Integer availableSeats;

    @Column(name = "fare", nullable = false)
    @NotNull
    private BigDecimal fare;

    @Column(name = "trip_date", nullable = false)
    @NotNull
    private LocalDateTime tripDate;

    
}