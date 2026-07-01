package com.busticket.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.busticket.dto.response.DriverTripSummaryResponse;
import com.busticket.dto.response.TripFullDetailsResponse;
import com.busticket.dto.response.TripResponse;
import com.busticket.entity.Bus;
import com.busticket.entity.Driver;
import com.busticket.entity.Route;
import com.busticket.entity.Trip;
import com.busticket.exception.ResourceNotFoundException;
import com.busticket.mapper.response.TripMapper;
import com.busticket.mapper.response.TripResponseMapper;
import com.busticket.respository.IRouteRepo;
import com.busticket.respository.ITripRepo;
import com.busticket.service.interfaces.ITripService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements ITripService {
	private final ITripRepo repo;
	private final TripMapper tripMapper;
	private final IRouteRepo routeRepo;

	@Override
	public List<TripResponse> getTripsWithAvailableSeats( int val) {

		return repo.findByAvailableSeatsGreaterThan(val).stream().map(e -> TripResponseMapper.entityToResponse(e))
				.toList();
	}
	
	@Override
	public List<DriverTripSummaryResponse> getCompletedTrips(String fromCity, String toCity) {

	    List<DriverTripSummaryResponse> list1 =
	            Optional.ofNullable(repo.getTripsByDriver1(fromCity, toCity))
	                    .orElse(Collections.emptyList());

	    List<DriverTripSummaryResponse> list2 =
	            Optional.ofNullable(repo.getTripsByDriver2(fromCity, toCity))
	                    .orElse(Collections.emptyList());

	    return Stream.concat(list1.stream(), list2.stream()).toList();
	}

    @Override
    @Transactional(readOnly = true)
    public TripFullDetailsResponse getTripById(Integer tripId) {

        Trip trip = repo.findById(tripId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Trip not found with ID: " + tripId));


        Route route = trip.getRoute();
        Bus bus = trip.getBus();

        List<Driver> drivers = new ArrayList<>();
        if (trip.getDriver1() != null) drivers.add(trip.getDriver1());
        if (trip.getDriver2() != null) drivers.add(trip.getDriver2());


        return tripMapper.tripToFullDetailsResponse(trip, route, bus, drivers);
    }

	@Override
	public List<TripResponse> getTripsWithAvailableSeatsInARoute(int val,int id) {
		if(!routeRepo.existsById(id)) {
			throw new ResourceNotFoundException("Route not found with ID: " + id);
		}
		
		return repo.findByAvailableSeatsGreaterThanAndRoute_RouteId(val,id).stream().map(e -> TripResponseMapper.entityToResponse(e))
				.toList();
	}


}



