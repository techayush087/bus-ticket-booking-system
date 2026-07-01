package com.busticket.mapper.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.busticket.dto.response.RouteWithTripsDTO;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;

@Component
public class RouteWithTripsResponseMapper {

    public RouteWithTripsDTO routeWithTripsToResponse(Route route, List<Trip> trips) {

        return new RouteWithTripsDTO(
                route.getRouteId(),
                trips
        );
    }
}