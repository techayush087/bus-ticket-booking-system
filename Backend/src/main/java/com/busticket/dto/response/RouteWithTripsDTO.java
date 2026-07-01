package com.busticket.dto.response;

import java.util.List;

import com.busticket.entity.Trip;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteWithTripsDTO {

    private Integer routeId;
  
    private List<Trip> trips;
}