package com.busticket.service.interfaces;

import com.busticket.dto.response.RouteWithTripsDTO;

public interface IRouteService {
	public RouteWithTripsDTO getRouteWithTrips(Integer routeId);
}
