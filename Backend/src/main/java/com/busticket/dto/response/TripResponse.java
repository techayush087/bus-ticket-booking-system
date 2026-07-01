// TripResponse.java
package com.busticket.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TripResponse {

    private Integer tripId;

    // Route summary
    private Integer routeId;
    private String fromCity;
    private String toCity;
    private Integer breakPoints;
    private String durationFormatted;       // e.g. "14h 30m"

    // Bus summary
    private Integer busId;
    private String busRegistrationNumber;
    private String busType;
    private Integer busCapacity;

    // Boarding & dropping addresses
    private Integer boardingAddressId;
    private String boardingAddress;
    private String boardingCity;

    private Integer droppingAddressId;
    private String droppingAddress;
    private String droppingCity;

    // Driver summaries
    private Integer driver1Id;
    private String driver1Name;
    private String driver1Phone;

    private Integer driver2Id;
    private String driver2Name;
    private String driver2Phone;

    // Trip details
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime tripDate;
    private Integer availableSeats;
    private BigDecimal fare;
}