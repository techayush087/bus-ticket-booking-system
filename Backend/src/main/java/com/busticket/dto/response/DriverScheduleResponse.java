package com.busticket.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverScheduleResponse {

    private Integer driverId;
    private String driverName;

    private List<TripDetails> upcomingTrips;
}