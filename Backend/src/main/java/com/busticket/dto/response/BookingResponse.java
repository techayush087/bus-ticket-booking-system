package com.busticket.dto.response;

import com.busticket.entity.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
public class BookingResponse {

    private Integer bookingId;

    // Trip summary fields (avoid full TripResponse nesting)
    private Integer tripId;
    private String fromCity;
    private String toCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal fare;

    private Integer seatNumber;
    private BookingStatus status;

    private Integer customerId;
    private String customerName;

}
