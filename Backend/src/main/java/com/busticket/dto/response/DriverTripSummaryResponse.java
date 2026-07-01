package com.busticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverTripSummaryResponse {

    private Integer driverId;
    private String driverName;
    private String fromCity;
    private String toCity;
    private Long totalTrips;
}
