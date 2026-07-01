package com.busticket.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TripFullDetailsResponse {

    private Integer tripId;
    private RouteResponse route;
    private BusResponse bus;
    private List<DriverResponse> drivers;
}
